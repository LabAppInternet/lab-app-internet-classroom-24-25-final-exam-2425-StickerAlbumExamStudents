package cat.tecnocampus.stickeralbum.application;

import cat.tecnocampus.stickeralbum.application.exceptions.BlindAuctionDoesNotExistException;
import cat.tecnocampus.stickeralbum.application.exceptions.CollectionWithStickerDoesNotExists;
import cat.tecnocampus.stickeralbum.application.exceptions.CollectorDoesNotExistException;
import cat.tecnocampus.stickeralbum.application.exceptions.StickerDoesNotExistException;
import cat.tecnocampus.stickeralbum.application.inputDTOs.BidCommand;
import cat.tecnocampus.stickeralbum.application.inputDTOs.BlindAuctionCommand;
import cat.tecnocampus.stickeralbum.application.outputDTOs.BidDTO;
import cat.tecnocampus.stickeralbum.application.outputDTOs.BlindAuctionDTO;
import cat.tecnocampus.stickeralbum.domain.Bid;
import cat.tecnocampus.stickeralbum.domain.BlindAuction;
import cat.tecnocampus.stickeralbum.domain.Collector;
import cat.tecnocampus.stickeralbum.domain.Sticker;
import cat.tecnocampus.stickeralbum.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlindAuctionService {
    private final CollectorRepository collectorRepository;
    private final HasStickerRepository hasStickerRepository;
    private final StickerRepository stickerRepository;
    private final BlindAuctionRepository blindAuctionRepository;
    private final BidRepository bidRepository;
    private final CollectionRepository collectionRepository;

    public BlindAuctionService(CollectorRepository collectorRepository, HasStickerRepository hasStickerRepository,
                               StickerRepository stickerRepository, BlindAuctionRepository blindAuctionRepository,
                               BidRepository bidRepository, CollectionRepository collectionRepository) {
        this.collectorRepository = collectorRepository;
        this.hasStickerRepository = hasStickerRepository;
        this.stickerRepository = stickerRepository;
        this.blindAuctionRepository = blindAuctionRepository;
        this.bidRepository = bidRepository;
        this.collectionRepository = collectionRepository;
    }

    @Transactional
    public void createBlindAuction(BlindAuctionCommand blindAuctionCommand) {
        Collector owner = collectorRepository.findById(blindAuctionCommand.ownerId())
                .orElseThrow(() -> new CollectorDoesNotExistException(blindAuctionCommand.ownerId()));
        Sticker sticker = stickerRepository.findById(blindAuctionCommand.stickerId())
                .orElseThrow(() -> new StickerDoesNotExistException(blindAuctionCommand.stickerId()));
        var collection = collectionRepository.findByCollectorAndSticker(owner, sticker)
                .orElseThrow(() -> new CollectionWithStickerDoesNotExists(owner.getId(), sticker.getId()));

        collection.blockSticker(sticker);
        BlindAuction blindAuction = new BlindAuction(owner, sticker, blindAuctionCommand.initialPrice(), blindAuctionCommand.beginDate(), blindAuctionCommand.endDate());
        blindAuctionRepository.save(blindAuction);
    }

    public void bidBlindly(BidCommand bidCommand) {
        Collector bidder = collectorRepository.findById(bidCommand.bidderId())
                .orElseThrow(() -> new CollectorDoesNotExistException(bidCommand.bidderId()));
        BlindAuction blindAuction = blindAuctionRepository.findById(bidCommand.auctionId())
                .orElseThrow(() -> new BlindAuctionDoesNotExistException(bidCommand.auctionId()));
        if(!blindAuction.isOpen()) {
            throw new IllegalStateException("Auction with Id " + bidCommand.auctionId() + " is closed");
        }
        if (bidCommand.amount() < blindAuction.getInitialPrice()) {
            throw new IllegalStateException("Bid offer quantity " + bidCommand.amount() + " is too low");
        }
        var bid = new Bid(blindAuction, bidder, bidCommand.amount());
        bidRepository.save(bid);
    }

    public List<BlindAuctionDTO> getOpenBlindAuctionsOfSticker(Long stickerId) {
        return blindAuctionRepository.findOpenBlindAuctionsOfSticker(stickerId);
    }

    public List<BidDTO> getBidsOfAuction(Long auctionId) {
        return bidRepository.findBidsByAuctionId(auctionId);
    }

    // TODO 4.1 This method now returns the oldest bid of the auction. Rewrite the query and the method so that it returns the winner bid of the auciton.
    //  the winner bid is the bid with the highest offer and the oldest date. In case that there are ties, the winner bid is randomly selected among the tied bids.
    public Optional<Bid> getWinnerBidOfAuction(Long auctionId) {
        var bids = bidRepository.findHighestBidsByAuctionId(auctionId); //This query should be rewritten (TO DO 4.2)
        if (bids.isEmpty()) {
            return Optional.empty();
        }
        // TODO 4.3 Rewrite this part of the method to get the winner bid randomly among the oldest bids.
        //  Note that if the previous query is correctly rewritten, *bids* contains the highest bids ordered by date.
        return Optional.ofNullable(bids.get(0));
    }

    @Transactional
    public void finishAuctionAndSetWinner(BlindAuction auction) {
        if (!auction.isPast() || auction.isFinished())  {
            throw new IllegalStateException("Auction with Id " + auction.getId() + " is either still open or already finished");
        }
        var winnerBid = getWinnerBidOfAuction(auction.getId()).orElse(null);
        if (winnerBid != null) {
            giveStickerToWinner(auction, winnerBid);
        }
        auction.setWinningBid(winnerBid);
    }

    public BidDTO getWinnerBidOfAuctionDTO(Long auctionId) {
        var auction = blindAuctionRepository.findById(auctionId).orElseThrow(() -> new BlindAuctionDoesNotExistException(auctionId));
        return getWinnerBidOfAuction(auctionId).map( bid -> new BidDTO(bid.getBidder().getId(), bid.getBidder().getName(), auctionId, auction.getSticker().getId(),
                        auction.getSticker().getName(), bid.getOffer(), bid.getDate()))
                .orElse(null);
    }

    public void giveStickerToWinner(BlindAuction auction, Bid winnerBid) {
        var originCollection = collectionRepository.findByCollectorAndSticker(auction.getOwner(), auction.getSticker())
                .orElseThrow(() -> new IllegalStateException("Origin collection not found"));
        originCollection.removeBlockedSticker(auction.getSticker());
        var winnerCollection = collectionRepository.findByCollectorAndSticker(winnerBid.getBidder(), auction.getSticker())
                .orElseThrow(() -> new IllegalStateException("Winner collection not found"));
        winnerCollection.addSticker(auction.getSticker(), 1);
    }

    @Transactional
    public void finishClosedAuctions() {
        var auctions = blindAuctionRepository.findClosedNotFinishedBlindAuctions();
        auctions.forEach(auction -> {
            try {
                finishAuctionAndSetWinner(auction);
            } catch (Exception e) {
                System.out.println("Error finishing auction " + auction.getId() + ": " + e.getMessage());
            }
        });
    }
}

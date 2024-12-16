package cat.tecnocampus.stickeralbum.persistence;

import cat.tecnocampus.stickeralbum.application.outputDTOs.BidDTO;
import cat.tecnocampus.stickeralbum.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {

    // TODO 4.2 Rewrite the following query so that it retrieves the highest bids for a given auction ordered by date
    //   For example, if the bids are (id, amount, date): (1, 10, 01-12-2024), (2, 12, 02-12-2024), (3, 12, 02-12-2024), (4, 12, 03-12-2024) (5, 11, 02-12-2024).
    //   The query should return these bids in this order: (2, 12, 02-12-2024), (3, 12, 02-12-2024), (4, 12, 03-12-2024)
    @Query("""
        SELECT b
        FROM Bid b 
        WHERE b.auction.id = :auctionId 
        ORDER BY b.date ASC
    """)
    List<Bid> findHighestBidsByAuctionId(@Param("auctionId") Long auctionId);

    @Query("""
        SELECT new cat.tecnocampus.stickeralbum.application.outputDTOs.BidDTO(b.bidder.id, b.bidder.email, 
            b.auction.id, b.auction.sticker.id, b.auction.sticker.name, b.offer, b.date)
        FROM Bid b 
        WHERE b.auction.id = :auctionId 
        ORDER BY b.date ASC
    """)
    List<BidDTO> findBidsByAuctionId(Long auctionId);
}

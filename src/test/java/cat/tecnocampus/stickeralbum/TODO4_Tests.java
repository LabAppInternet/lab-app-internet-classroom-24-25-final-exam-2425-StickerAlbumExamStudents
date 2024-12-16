package cat.tecnocampus.stickeralbum;

import cat.tecnocampus.stickeralbum.application.BlindAuctionService;
import cat.tecnocampus.stickeralbum.persistence.BidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TODO4_Tests {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BlindAuctionService blindAuctionService;

    @Test
    void queryOfHighestBidTest() {
        var bids = bidRepository.findHighestBidsByAuctionId(4L);

        assertEquals(3, bids.size());
        assertEquals(2, bids.get(0).getBidder().getId());
        assertEquals(12, bids.get(0).getOffer());
        assertEquals(LocalDate.now().plusDays(-2), bids.get(0).getDate());
        assertEquals(3, bids.get(1).getBidder().getId());
        assertEquals(12, bids.get(1).getOffer());
        assertEquals(LocalDate.now().plusDays(-2), bids.get(1).getDate());
        assertEquals(4, bids.get(2).getBidder().getId());
        assertEquals(12, bids.get(2).getOffer());
        assertEquals(LocalDate.now().plusDays(-1), bids.get(2).getDate());
    }

    @Test
    void winningBidTest() {
        var bid = blindAuctionService.getWinnerBidOfAuction(4L).get();

        assertTrue(bid.getBidder().getId() == 2 || bid.getBidder().getId() == 3);
    }
}


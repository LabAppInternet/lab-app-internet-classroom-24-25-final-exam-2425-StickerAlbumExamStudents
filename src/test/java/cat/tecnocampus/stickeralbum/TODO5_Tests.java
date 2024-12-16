package cat.tecnocampus.stickeralbum;

import cat.tecnocampus.stickeralbum.application.CollectionService;
import cat.tecnocampus.stickeralbum.application.exceptions.CollectorDoesNotExistException;
import cat.tecnocampus.stickeralbum.persistence.CollectionRepository;
import cat.tecnocampus.stickeralbum.persistence.CollectorRepository;
import cat.tecnocampus.stickeralbum.persistence.ExchangeOfStickersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc

public class TODO5_Tests {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ExchangeOfStickersRepository exchangeOfStickersRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private CollectorRepository collectorRepository;

    private Long collectorId1 = 3L;
    private Long collectorId2 = 4L;
    private Long albumId = 1L;

    @BeforeEach
    @DirtiesContext
    void setUp() {
        collectionService.exchangeProposal(collectorId1, collectorId2, albumId);
    }


    @Test
    @DirtiesContext
    void exchangeProposal() {
        var exchanges = collectionService.getPendingExchangesByDestination(collectorId2);
        assertEquals(1, exchanges.size());
        var exchange = exchanges.get(0);
        assertEquals(collectorId2, exchange.getDestinationCollectorId());
        assertEquals(collectorId1, exchange.getOriginCollectorId());
        assertEquals(2, exchange.getGive().size());
        assertEquals(2, exchange.getReceive().size());
        assertEquals(1, exchange.getGive().get(0).getId());
        assertEquals(3, exchange.getReceive().get(0).getId());
    }

    // Test for TO DO 5.2
    /*
    @Test
    void getExchangeProposalsQuery() {
        var collector = collectorRepository.findById(collectorId2).get();
        var exchanges = exchangeOfStickersRepository.findPendingByDestinationUser(collector);
        assertEquals(1, exchanges.size());
        var exchange = exchanges.get(0);
        assertEquals(collectorId2, exchange.getDestinationCollectorId());
        assertEquals(collectorId1, exchange.getOriginCollectorId());
    }
    */

    // Test for TO DO 5.3
    /*
    @Test
    void getGiveStickersQuery() {
        var stickers = exchangeOfStickersRepository.findExchangeGiveStickers(1L);
        assertEquals(2, stickers.size());
        assertEquals(3, stickers.get(0).getId());
        assertEquals(4, stickers.get(1).getId());
    }
    */

    // Test for TO DO 5.4
    /*
    @Test
    void getReceiveStickersQuery() {
        var stickers = exchangeOfStickersRepository.findExchangeReceiveStickers(1L);
        assertEquals(2, stickers.size());
        assertEquals(1, stickers.get(0).getId());
        assertEquals(2, stickers.get(1).getId());
    }
     */

    @Test
    void getPendingExchangesByDestination_throwsCollectorDoesNotExistException() {
        Long nonExistentCollectorId = 999L; // Use an ID that does not exist in the database

        assertThrows(CollectorDoesNotExistException.class, () -> {
            collectionService.getPendingExchangesByDestination(nonExistentCollectorId);
        });
    }
}

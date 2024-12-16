package cat.tecnocampus.stickeralbum;

import cat.tecnocampus.stickeralbum.application.CollectionService;
import cat.tecnocampus.stickeralbum.application.exceptions.CollectorDoesNotExistException;
import cat.tecnocampus.stickeralbum.persistence.AlbumRepository;
import cat.tecnocampus.stickeralbum.persistence.CollectorRepository;
import cat.tecnocampus.stickeralbum.persistence.HasStickerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = "/deleteAuctionsForTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext
public class TODO6_Tests {
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private HasStickerRepository hasStickerRepository;
    @Autowired
    private CollectorRepository collectorRepository;
    @Autowired
    private AlbumRepository albumRepository;

    @Test
    void getCollectedStickersMethodCall() {
        var collectedStickers = collectionService.getCollectedStickers(1L, 1L);
        assertEquals(1, collectedStickers.getAlbumId());
        assertEquals(1, collectedStickers.getCollectorId());
        assertEquals(2, collectedStickers.getSections().get(0).getStickers().size());
        assertEquals(1, collectedStickers.getSections().get(0).getStickers().get(0).getQuantity());
        assertEquals(3, collectedStickers.getSections().get(0).getStickers().get(1).getQuantity());
        assertEquals(3, collectedStickers.getSections().get(1).getStickers().get(0).getQuantity());
        assertEquals(3, collectedStickers.getSections().get(2).getStickers().get(0).getQuantity());
    }

    @Test
    void getCollectedStickersUnexistingCollector() {
        assertThrows(CollectorDoesNotExistException.class, () -> {
            collectionService.getCollectedStickers(999L, 1L);
        });
    }

    @Test
    void getCollectedStickersUnexistingAlbum() {
        assertThrows(CollectorDoesNotExistException.class, () -> {
            collectionService.getCollectedStickers(1L, 999L);
        });
    }

    /* TO DO 6.3
    @Test
    void testQuery() {
        var collector = collectorRepository.findById(1L).get();
        var album = albumRepository.findById(1L).get();
        var hasStickerDTOs = hasStickerRepository.findAllByCollectionOrderBySectionAndNumberAsc(album, collector);

        // check that the query returns 4 stickers and have ids 1,2,4,5 and quantities 1,3,3,3
        assertEquals(4, hasStickerDTOs.size());
        assertEquals(1, hasStickerDTOs.get(0).stickerId());
        assertEquals(2, hasStickerDTOs.get(1).stickerId());
        assertEquals(4, hasStickerDTOs.get(2).stickerId());
        assertEquals(5, hasStickerDTOs.get(3).stickerId());
        assertEquals(1, hasStickerDTOs.get(0).numberOfCopies());
        assertEquals(3, hasStickerDTOs.get(1).numberOfCopies());
        assertEquals(3, hasStickerDTOs.get(2).numberOfCopies());
        assertEquals(3, hasStickerDTOs.get(3).numberOfCopies());
    }
     */
}

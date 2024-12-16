package cat.tecnocampus.stickeralbum;

import cat.tecnocampus.stickeralbum.application.CollectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Sql(scripts = "/deleteAuctionsForTest.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@DirtiesContext
public class TODO7_Tests {
    @Autowired
    private CollectionService collectionService;

    @Test
    void getCollectorRanking() {
        var ranking = collectionService.getCollectorsRanking();

        assertTrue(ranking.size() == 5 || ranking.size() == 6);
        assertEquals("Josep", ranking.get(0).collectorName());
        assertEquals(17, ranking.get(0).numberOfStickers());
        assertEquals("Tina", ranking.get(1).collectorName());
        assertEquals(15, ranking.get(1).numberOfStickers());
        assertEquals("Tina", ranking.get(2).collectorName());
        assertTrue(ranking.get(2).numberOfStickers() == 9 || ranking.get(2).numberOfStickers() == 8);
        assertEquals("Maria", ranking.get(3).collectorName());
        assertEquals(7, ranking.get(3).numberOfStickers());
        assertEquals("Antonio", ranking.get(4).collectorName());
        assertEquals(3, ranking.get(4).numberOfStickers());
    }
}

package cat.tecnocampus.stickeralbum;

import cat.tecnocampus.stickeralbum.application.BlindAuctionService;
import cat.tecnocampus.stickeralbum.application.inputDTOs.BlindAuctionCommand;
import cat.tecnocampus.stickeralbum.domain.CollectionPK;
import cat.tecnocampus.stickeralbum.domain.HasStickerPK;
import cat.tecnocampus.stickeralbum.persistence.HasStickerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InitiallyWorkingTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BlindAuctionService blindAuctionService;
   @Autowired
    private HasStickerRepository hasStickerRepository;

    @Test
    @DirtiesContext
    void createAlbumHappyPath() throws Exception {
        String today = LocalDate.now().toString();
        String inAMonth = LocalDate.now().plusMonths(1).toString();
        String album = """
                {
                  "name": "Heidi in the Mountains",
                  "editor": "Pannini",
                  "begins": "%s",
                  "ends": "%s",
                  "ownerId": 3,
                  "sections": [
                    {"name":  "At home",
                    "stickers":  [
                      {"name": "Heidi playing", "number": 1, "description": {"title": "Heidi","place": "At home"}},
                      {"name": "Peter counting goats", "number": 2, "description": {"title": "Peter","place": "At home"}},
                      {"name": "Grandfather doing cheese", "number": 3, "description": {"title": "Grandfather","place": "At home"}}
                    ]},
                    {"name":  "At mountains",
                      "stickers":  [
                        {"name": "Heidi running uphill", "number": 4, "description": {"title": "Heidi","place": "At mountains"}},
                        {"name": "Peter guiding goats", "number": 5, "description": {"title": "Peter","place": "At mountains"}},
                        {"name": "Grandfather looking for Heidi", "number": 6, "description": {"title": "Grandfather","place": "At mountains"}}
                      ]}
                  ]
                }""".formatted(today, inAMonth);

        mockMvc.perform(post("/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(album))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/albums/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Heidi in the Mountains"))
                .andExpect(jsonPath("$.begins").value(today))
                .andExpect(jsonPath("$.ends").value(inAMonth))
                .andExpect(jsonPath("$.sections").isArray())
                .andExpect(jsonPath("$.sections[0].name").value("At home"))
                .andExpect(jsonPath("$.sections[1].name").value("At mountains"));
    }

    @Test
    @DirtiesContext
    void createCollection() throws Exception {
        String today = LocalDate.now().toString();
        String inAMonth = LocalDate.now().plusMonths(1).toString();
        String collection = """
                {
                  "albumId": 1,
                  "collectorId": 6,
                  "beginDate": "%s",
                  "endDate": "%s"
                }""".formatted(today, inAMonth);

        mockMvc.perform(post("/collections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(collection))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/collectors/6/collections"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].albumId").value(1))
                .andExpect(jsonPath("$[0].collectorId").value(6))
                .andExpect(jsonPath("$[0].numberOfDifferentStickers").value(0))
                .andExpect(jsonPath("$[0].numberOfStickersInAlbum").value(6));
    }

    @Test
    @DirtiesContext
    void addStickerToCollectionAlreadyHas() throws Exception {
        String addStickerCommand = """
                {
                  "collectorId": 1,
                  "albumId": 1,
                  "stickerNumber": 1,
                  "numberOfCopies": 2
                }""";

        mockMvc.perform(post("/collectors/stickers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addStickerCommand))
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    void addStickerToCollectionDoesNotHave() throws Exception {
        String addStickerCommand = """
                {
                  "collectorId": 1,
                  "albumId": 1,
                  "stickerNumber": 3,
                  "numberOfCopies": 2
                }""";

        mockMvc.perform(post("/collectors/stickers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addStickerCommand))
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    void createBlindAuction() throws Exception {
        String blindAuction = """
                {
                  "ownerId": 1,
                  "stickerId": 2,
                  "initialPrice": 10,
                  "beginDate": "%s",
                  "endDate": "%s"
                }""".formatted(LocalDate.now().toString(), LocalDate.now().plusDays(1).toString());

        mockMvc.perform(post("/collectors/blindAuctions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blindAuction))
                .andExpect(status().isCreated());

        var auctions = blindAuctionService.getOpenBlindAuctionsOfSticker(2L);
        //test the content of auctions. Same as in the next code snippet
        // Ensure the auctions list is not null and has the expected size
        assertNotNull(auctions);
        assertEquals(2, auctions.size());

        // Verify the properties of the first auction
        var auction = auctions.get(1);
        assertEquals(1L, auction.ownerId());
        assertEquals(10, auction.initialPrice());
        assertEquals(LocalDate.now(), auction.beginDate());

        var hasSticker = hasStickerRepository.findById(new HasStickerPK(new CollectionPK(1L, 1L), 2L)).get();
        assertNotNull(hasSticker);
        assertEquals(true, hasSticker.hasBlockedCopy());

    }

    @Test
    @DirtiesContext
    void bidToBlindAuction() throws Exception {

        blindAuctionService.createBlindAuction(new BlindAuctionCommand(1L, 2L, 10.0, LocalDate.now(), LocalDate.now().plusDays(1)));

        String bidCommand = """
                {
                  "bidderId": 2,
                  "auctionId": 5,
                  "amount": 15
                }""";
        mockMvc.perform(post("/blindAuctions/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidCommand))
                .andExpect(status().isCreated());

        var bids = blindAuctionService.getBidsOfAuction(5L);
        assertNotNull(bids);
        assertEquals(1, bids.size());
        var bid = bids.get(0);
        assertEquals(2L, bid.bidderId());
        assertEquals(15.0, bid.amount());
    }
}

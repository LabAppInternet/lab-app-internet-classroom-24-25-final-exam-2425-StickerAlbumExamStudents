package cat.tecnocampus.stickeralbum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TODO2_Tests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createBlindAuctionWithUnExistingCollector() throws Exception {
        String auction = """
                {
                    "ownerId": 27,
                    "stickerId": 27,
                    "initialPrice": 10,
                    "beginDate": "2025-01-01",
                    "endDate": "2026-01-01"
                }""";

        mockMvc.perform(post("/collectors/blindAuctions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(auction))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Collector with id 27 does not exist"));
    }

    @Test
    void createBlindAuctionWithUnExistingSticker() throws Exception {
        String auction = """
                {
                    "ownerId": 1,
                    "stickerId": 27,
                    "initialPrice": 10,
                    "beginDate": "2025-01-01",
                    "endDate": "2026-01-01"
                }""";

        mockMvc.perform(post("/collectors/blindAuctions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(auction))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Sticker with id 27 does not exist"));
    }

    @Test
    void getAlbumWithUnExistingId() throws Exception {
        mockMvc.perform(get("/albums/{albumId}", 999)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Album with id 999 does not exist"));
    }

    @Test
    void getExchangeableStickersWithNonExistingCollection() throws Exception {
        mockMvc.perform(get("/collectors/{collectorId1}/collector/{collectorId2}/albums/{albumId}/exchangeableStickers", 1, 2, 3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Collection with AlbumId 3 and CollectorId: 1 does not exist"));
    }

    @Test
    void bidToUnExistingOBlindAuction() throws Exception {

        String bidCommand = """
                {
                  "bidderId": 2,
                  "auctionId": 999,
                  "amount": 15
                }""";
        mockMvc.perform(post("/blindAuctions/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bidCommand))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("BlindAuction with id 999 does not exist"));
    }
}

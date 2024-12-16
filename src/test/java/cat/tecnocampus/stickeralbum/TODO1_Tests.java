package cat.tecnocampus.stickeralbum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TODO1_Tests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAlbumWithErrors() throws Exception {
        String album = """
                {
                    "ownerId": null,
                    "name": "album",
                    "editor": "",
                    "begins": "2023-01-01",
                    "ends": "2022-01-01",
                    "sections": []
                }""";

        mockMvc.perform(post("/albums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(album))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(5)))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder(
                        "Owner id is mandatory",
                        "Name must begin with a capital letter and be larger than 5 characters",
                        "Editor must not be empty",
                        "Begin date must be in the future or present",
                        "End date must be in the future")));
    }

    @Test
    void addStickerWithErrors() throws Exception {
        String sticker = """
                {
                    "collectorId": null,
                    "albumId": null,
                    "stickerNumber": 0,
                    "numberOfCopies": 0
                }""";

        mockMvc.perform(post("/collectors/stickers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sticker))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(4)))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder(
                        "Collector id is mandatory",
                        "Album id is mandatory",
                        "Sticker number must be greater than 0",
                        "Number of copies must be greater than 0")));
    }

    @Test
    void createBlindAuctionWithErrors() throws Exception {
        String auction = """
                {
                    "ownerId": null,
                    "stickerId": null,
                    "initialPrice": 0,
                    "beginDate": "2022-01-01",
                    "endDate": "2022-01-01"
                }""";

        mockMvc.perform(post("/collectors/blindAuctions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(auction))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(5)))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder(
                        "Owner id is mandatory",
                        "Sticker id is mandatory",
                        "Initial price must be positive",
                        "Begin date must be in the future or present",
                        "End date must be in the future")));
    }

    @Test
    void bidWithErrors() throws Exception {
        String bid = """
                {
                    "bidderId": null,
                    "auctionId": null,
                    "amount": 0
                }""";

        mockMvc.perform(post("/blindAuctions/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(3)))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder(
                        "Bidder id is mandatory",
                        "Auction id is mandatory",
                        "Amount must be positive")));
    }

    @Test
    void createCollectionWithErrors() throws Exception {
        String collection = """
                {
                    "collectorId": null,
                    "albumId": null,
                    "beginDate": "2022-01-01",
                    "endDate": "2022-01-01"
                }""";

        mockMvc.perform(post("/collections")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(collection))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations", hasSize(4)))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder(
                        "Collector id is mandatory",
                        "Album id is mandatory",
                        "Begin date must be in the future or present",
                        "End date must be in the future")));
    }

}

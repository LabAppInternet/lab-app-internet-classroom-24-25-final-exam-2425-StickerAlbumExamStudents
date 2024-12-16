package cat.tecnocampus.stickeralbum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TODO3_Tests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void singleExchangeWithStickersNotFromSameCollection() throws Exception {
        String command = """
                {
                    "collectorId1": 1,
                    "stickerId1": 7,
                    "collectorId2": 2,
                    "stickerId2": 2
                }""";
       mockMvc.perform(post("/collections/singleExchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(command))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Domain error. Stickers are not from the same album"));
    }

    @Test
    void bidOnClosedBlindAuction() throws Exception {
        String bid = """
                {
                    "bidderId": 2,
                    "auctionId": 1,
                    "amount": 14
                }""";
        mockMvc.perform(post("/blindAuctions/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Domain error. Auction with Id 1 is closed"));
    }

    @Test
    void bidOnBlindAuctionTooLow() throws Exception {
        String bid = """
                {
                    "bidderId": 2,
                    "auctionId": 4,
                    "amount": 2
                }""";
        mockMvc.perform(post("/blindAuctions/bids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bid))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Domain error. Bid offer quantity 2.0 is too low"));
    }
}

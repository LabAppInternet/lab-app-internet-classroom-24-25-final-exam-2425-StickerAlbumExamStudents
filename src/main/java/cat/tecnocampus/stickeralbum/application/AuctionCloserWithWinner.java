package cat.tecnocampus.stickeralbum.application;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AuctionCloserWithWinner {
    private final BlindAuctionService blindAuctionService;

    public AuctionCloserWithWinner(BlindAuctionService blindAuctionService) {
        this.blindAuctionService = blindAuctionService;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void closeAndSetWinnerOfAuctions() {
        blindAuctionService.finishClosedAuctions();
    }
}

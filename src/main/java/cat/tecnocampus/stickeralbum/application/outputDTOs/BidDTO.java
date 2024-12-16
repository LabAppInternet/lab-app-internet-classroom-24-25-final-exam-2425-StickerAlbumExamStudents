package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.time.LocalDate;

public record BidDTO(Long bidderId, String bidderName, Long auctionId, Long stickerId, String stickerName, Double amount, LocalDate date) {
}

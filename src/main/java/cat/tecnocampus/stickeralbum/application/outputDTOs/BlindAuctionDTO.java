package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.time.LocalDate;

public record BlindAuctionDTO(Long ownerId, String ownerEmail, Long stickerId, String stickerName, Double initialPrice, LocalDate beginDate, LocalDate endDate) {
}

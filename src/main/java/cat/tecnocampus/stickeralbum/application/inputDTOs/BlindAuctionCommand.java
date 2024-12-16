package cat.tecnocampus.stickeralbum.application.inputDTOs;

import java.time.LocalDate;

public record BlindAuctionCommand(
        Long ownerId,
        Long stickerId,
        Double initialPrice,
        LocalDate beginDate,
        LocalDate endDate) {
}

package cat.tecnocampus.stickeralbum.application.inputDTOs;

import java.time.LocalDate;

public record CollectionCommand(
        Long collectorId,
        Long albumId,
        LocalDate beginDate,
        LocalDate endDate) {
}

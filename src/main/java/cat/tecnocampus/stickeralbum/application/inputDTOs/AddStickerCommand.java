package cat.tecnocampus.stickeralbum.application.inputDTOs;

public record AddStickerCommand(
        Long collectorId,
        Long albumId,
        Long stickerNumber,
        int numberOfCopies) {
}

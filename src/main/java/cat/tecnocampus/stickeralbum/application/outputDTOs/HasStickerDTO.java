package cat.tecnocampus.stickeralbum.application.outputDTOs;

public record HasStickerDTO(
        Long collectorId,
        String collectorName,
        String albumName,
        Long albumId,
        String sectionName,
        Long sectionId,
        String stickerName,
        Long stickerNumber,
        Long stickerId,
        int numberOfCopies) {
}

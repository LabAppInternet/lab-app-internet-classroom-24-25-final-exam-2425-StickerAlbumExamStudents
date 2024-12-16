package cat.tecnocampus.stickeralbum.application.outputDTOs;

public record CollectionDTO(Long albumId, String albumName, Long collectorId, String collectorName, int numberOfDifferentStickers, long numberOfStickersInAlbum) {
}

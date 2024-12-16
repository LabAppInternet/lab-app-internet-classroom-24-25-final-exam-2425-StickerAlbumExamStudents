package cat.tecnocampus.stickeralbum.application.exceptions;

public class CollectionDoesNotExistException extends RuntimeException {
    public CollectionDoesNotExistException(Long albumId, Long collectorId) {
        super("Collection with AlbumId " + albumId + " and CollectorId: " + collectorId + " does not exist");
    }
}
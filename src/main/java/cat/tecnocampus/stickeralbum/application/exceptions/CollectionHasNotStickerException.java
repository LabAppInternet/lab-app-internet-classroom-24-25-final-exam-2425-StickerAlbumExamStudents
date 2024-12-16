package cat.tecnocampus.stickeralbum.application.exceptions;

public class CollectionHasNotStickerException extends RuntimeException {
    public CollectionHasNotStickerException(Long collectorId, Long albumId, Long stickerId) {
            super("Collection with ownerId " + collectorId + ", albumId " + albumId + " does nto contain stickerId " + stickerId);
        }
}

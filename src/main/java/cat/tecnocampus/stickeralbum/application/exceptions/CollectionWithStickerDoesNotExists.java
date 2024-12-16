package cat.tecnocampus.stickeralbum.application.exceptions;

public class CollectionWithStickerDoesNotExists extends RuntimeException {
    public CollectionWithStickerDoesNotExists(Long collectorId, Long stickerId) {
        super("Collection with ownerId " + collectorId +  " and containing stickerId " + stickerId + " does not exist");
    }
}

package cat.tecnocampus.stickeralbum.application.exceptions;

public class StickerNotInCollectionException extends RuntimeException {
    public StickerNotInCollectionException(Long stickerId, Long collectorId) {
        super("Sticker with id " + stickerId + " is not in the collection of collector with id: ");
    }
}

package cat.tecnocampus.stickeralbum.application.exceptions;

public class StickerDoesNotExistException extends RuntimeException {
    public StickerDoesNotExistException(Long id) {
        super("Sticker with id " + id + " does not exist");
    }
}

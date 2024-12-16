package cat.tecnocampus.stickeralbum.application.exceptions;

public class ExchangeDoesNotExistException extends RuntimeException {
    public ExchangeDoesNotExistException(Long id) {
        super("Exchange with id " + id + " does not exist");
    }
}

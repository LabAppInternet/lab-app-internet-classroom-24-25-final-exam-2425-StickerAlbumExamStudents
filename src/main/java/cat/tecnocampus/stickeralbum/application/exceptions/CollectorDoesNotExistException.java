package cat.tecnocampus.stickeralbum.application.exceptions;

public class CollectorDoesNotExistException extends RuntimeException {
    public CollectorDoesNotExistException(Long id) {
        super("Collector with id " + id + " does not exist");
    }
}

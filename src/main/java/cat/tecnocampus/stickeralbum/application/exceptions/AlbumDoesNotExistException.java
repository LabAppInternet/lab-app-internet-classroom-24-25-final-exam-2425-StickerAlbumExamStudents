package cat.tecnocampus.stickeralbum.application.exceptions;

public class AlbumDoesNotExistException extends RuntimeException {
    public AlbumDoesNotExistException(Long id) {
        super("Album with id " + id + " does not exist");
    }
}
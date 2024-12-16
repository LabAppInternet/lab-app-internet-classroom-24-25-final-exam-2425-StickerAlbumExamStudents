package cat.tecnocampus.stickeralbum.application.exceptions;

public class AlbumNotActiveException extends RuntimeException {
    public AlbumNotActiveException(Long id) {
        super("Album with id " + id + " is not active");
    }
}

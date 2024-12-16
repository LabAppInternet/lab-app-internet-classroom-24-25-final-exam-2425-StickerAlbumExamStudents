package cat.tecnocampus.stickeralbum.application.exceptions;

public class BlindAuctionDoesNotExistException extends RuntimeException {
    public BlindAuctionDoesNotExistException(Long id) {
        super("BlindAuction with id " + id + " does not exist");
    }
}

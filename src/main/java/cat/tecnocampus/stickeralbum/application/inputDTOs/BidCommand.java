package cat.tecnocampus.stickeralbum.application.inputDTOs;

public record BidCommand(
        Long bidderId,
        Long auctionId,
        Double amount) {
}

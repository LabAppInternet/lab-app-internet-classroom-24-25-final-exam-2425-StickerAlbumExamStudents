package cat.tecnocampus.stickeralbum.application.inputDTOs;

public record ExchangeStickersCommand(Long collectorId1, Long stickerId1, Long collectorId2, Long stickerId2) {
}

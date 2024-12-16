package cat.tecnocampus.stickeralbum.application.outputDTOs;

public class CollectedStickerDTO {
    Long stickerId;
    String stickerName;
    Long stickerNumber;
    int quantity;

    public CollectedStickerDTO(Long stickerId, String stickerName, Long stickerNumber, int quantity) {
        this.stickerId = stickerId;
        this.stickerName = stickerName;
        this.stickerNumber = stickerNumber;
        this.quantity = quantity;
    }
    public Long getStickerId() {
        return stickerId;
    }

    public String getStickerName() {
        return stickerName;
    }

    public Long getStickerNumber() {
        return stickerNumber;
    }

    public int getQuantity() {
        return quantity;
    }
}

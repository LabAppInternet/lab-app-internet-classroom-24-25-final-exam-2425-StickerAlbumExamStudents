package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.util.ArrayList;
import java.util.List;

public class CollectedStickersSectionDTO {
    Long sectionId;
    String sectionName;
    List<CollectedStickerDTO> stickers;

    public CollectedStickersSectionDTO() {
        stickers = new ArrayList<>();
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<CollectedStickerDTO> getStickers() {
        return stickers;
    }

    public void addSticker(CollectedStickerDTO sticker) {
        this.stickers.add(sticker);
    }
}

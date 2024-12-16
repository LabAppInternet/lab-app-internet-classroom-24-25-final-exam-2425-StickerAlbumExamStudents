package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.util.List;

public class SectionDTO {

    private long id;
    private String name;
    private List<StickerDTO> stickers;

    public SectionDTO(long id, String name, List<StickerDTO> stickers) {
        this.id = id;
        this.name = name;
        this.stickers = stickers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StickerDTO> getStickers() {
        return stickers;
    }

    public void setStickers(List<StickerDTO> stickers) {
        this.stickers = stickers;
    }
}
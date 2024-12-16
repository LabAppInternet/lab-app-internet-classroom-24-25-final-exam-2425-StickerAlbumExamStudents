package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.util.ArrayList;
import java.util.List;

public class CollectedStickersAlbumDTO {
    private Long albumId;
    private String albumName;
    private Long collectorId;
    private String collectorName;
    private List<CollectedStickersSectionDTO> sections;

    public CollectedStickersAlbumDTO() {
        sections = new ArrayList<>();
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectorId) {
        this.collectorId = collectorId;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
    }

    public List<CollectedStickersSectionDTO> getSections() {
        return sections;
    }

    public void addSection(CollectedStickersSectionDTO section) {
        this.sections.add(section);
    }
}

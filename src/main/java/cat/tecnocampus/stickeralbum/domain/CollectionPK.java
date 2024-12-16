package cat.tecnocampus.stickeralbum.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CollectionPK implements Serializable {
    @Column(name = "collector_id")
    Long collectorId;

    @Column(name = "album_id")
    Long albumId;

    public CollectionPK() {
    }

    public CollectionPK(Long collectorId, Long albumId) {
        this.collectorId = collectorId;
        this.albumId = albumId;
    }

    public Long getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Long collectionistId) {
        this.collectorId = collectionistId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CollectionPK that = (CollectionPK) o;
        return Objects.equals(collectorId, that.collectorId) && Objects.equals(albumId, that.albumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectorId, albumId);
    }
}

package cat.tecnocampus.stickeralbum.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HasStickerPK implements Serializable {
    @Embedded
    private CollectionPK collectionPK;

    @Column(name = "sticker_id")
    private Long stickerId;

    public HasStickerPK() {
    }

    public HasStickerPK(CollectionPK collectionPK, Long stickerId) {
        this.collectionPK = collectionPK;
        this.stickerId = stickerId;
    }

    public CollectionPK getCollectionPK() {
        return collectionPK;
    }

    public void setCollectionPK(CollectionPK collectionPK) {
        this.collectionPK = collectionPK;
    }

    public Long getStickerId() {
        return stickerId;
    }

    public void setStickerId(Long stickerId) {
        this.stickerId = stickerId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HasStickerPK that = (HasStickerPK) o;
        return Objects.equals(collectionPK, that.collectionPK) && Objects.equals(stickerId, that.stickerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionPK, stickerId);
    }
}

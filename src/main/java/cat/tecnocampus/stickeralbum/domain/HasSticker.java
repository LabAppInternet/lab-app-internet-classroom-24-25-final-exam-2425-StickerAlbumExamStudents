package cat.tecnocampus.stickeralbum.domain;

import jakarta.persistence.*;

@Entity
public class HasSticker {

    @EmbeddedId
    HasStickerPK hasStickerPK;

    @ManyToOne
    @JoinColumn(name="sticker_id", referencedColumnName="id")
    @MapsId("sticker_id")
    private Sticker sticker;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="collector_id", referencedColumnName="collector_id", insertable = false, updatable = false),
            @JoinColumn(name="album_id", referencedColumnName="album_id", insertable = false, updatable = false)
    })
    private Collection collection;

    private int numberOfCopies;
    private int blockedCopies;

    public HasSticker() {
    }

    public HasSticker(Sticker sticker, Collection collection, int numberOfCopies) {
        this.hasStickerPK = new HasStickerPK(collection.collectionPK, sticker.getId());
        this.numberOfCopies = numberOfCopies;
        this.sticker = sticker;
        this.collection = collection;
    }

    public void addCopies(int numberOfCopiesToAdd) {
        this.numberOfCopies += numberOfCopiesToAdd;
    }

    public int getTotalCopies() {
        return numberOfCopies;
    }

    public void blockCopy() {
        if (!hasSpareUnblockedCopy()) {
            throw new IllegalStateException("Collection with ownerId " + collection.getCollector().getId() + ", albumId " + collection.getAlbum().getId() + " does not have a free sticker with id " + sticker.getId());
        }
        blockedCopies++;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void removeCopyAndUnblock() {
        if (!hasBlockedCopy() || !hasCopy()) {
            throw new IllegalStateException("Collection with ownerId " + collection.getCollector().getId() + ", albumId " + collection.getAlbum().getId() + " does not have blocked copies available of sticker with id " + sticker.getId());
        }
        blockedCopies--;
        numberOfCopies--;
    }

    public boolean hasCopy() {
        return numberOfCopies > 0;
    }

    public boolean hasBlockedCopy() {
        return blockedCopies > 0;
    }

    public boolean hasSpareUnblockedCopy() {
        return numberOfCopies - blockedCopies - 1 > 0;
    }

    public void unblockCopy() {
        if (!hasBlockedCopy()) {
            throw new IllegalStateException("Collection with ownerId " + collection.getCollector().getId() + ", albumId " + collection.getAlbum().getId() + " does not have blocked copies available of sticker with id " + sticker.getId());
        }
        blockedCopies--;
    }
}

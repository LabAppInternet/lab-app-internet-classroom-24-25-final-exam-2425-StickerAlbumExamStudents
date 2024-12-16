package cat.tecnocampus.stickeralbum.domain;


import cat.tecnocampus.stickeralbum.application.exceptions.CollectionHasNotStickerException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Collection {

    @EmbeddedId
    CollectionPK collectionPK;

    @ManyToOne
    @JoinColumn(name="collector_id", referencedColumnName="id")
    @MapsId("collector_id")
    private Collector collector;

    @ManyToOne
    @JoinColumn(name="album_id", referencedColumnName="id")
    @MapsId("album_id")
    private Album album;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "collection")
    List<HasSticker> ownedStickers;

    private LocalDate beginDate;
    private LocalDate endDate;

    public Collection(Collector collector, Album album, LocalDate beginDate, LocalDate endDate) {
        this.collectionPK = new CollectionPK(collector.getId(), album.getId());
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.album = album;
        this.collector = collector;
        this.ownedStickers = new ArrayList<>();
    }

    public Collection() {
    }

    public Collector getCollector() {
        return collector;
    }

    public Album getAlbum() {
        return album;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void addSticker(Sticker sticker, int numberOfCopies) {
        // look for the sticker in the list of owned stickers and increment the number of copies. If not present add it.
        getHasSticker(sticker).ifPresentOrElse(
                hasSticker -> hasSticker.addCopies(numberOfCopies),
                () -> ownedStickers.add(new HasSticker(sticker, this, numberOfCopies))
        );
    }

    private Optional<HasSticker> getHasSticker(Sticker sticker) {
        return ownedStickers.stream()
                .filter(hasSticker -> hasSticker.getSticker().equals(sticker))
                .findFirst();
    }

    public int getStickerTotalCopies(Sticker sticker) {
        return getHasSticker(sticker)
                .map(HasSticker::getTotalCopies)
                .orElse(0);
    }

    public void removeBlockedSticker(Sticker sticker) {
        var hasSticker = getHasSticker(sticker).orElseThrow(() -> new RuntimeException("Not enough stickers"));
        if (hasSticker.getTotalCopies()  <= 0) throw new RuntimeException("Not enough stickers");
        hasSticker.removeCopyAndUnblock();
        if (!hasSticker.hasCopy()) {
            ownedStickers.remove(hasSticker);
        }
    }

    public void blockSticker(Sticker sticker) {
        var hasSticker = getHasSticker(sticker)
                .orElseThrow(() -> new CollectionHasNotStickerException(this.collector.getId(), this.album.getId(), sticker.getNumber()));
        hasSticker.blockCopy();
    }

    public void unblockSticker(Sticker sticker) {
        var hasSticker = getHasSticker(sticker)
                .orElseThrow(() -> new CollectionHasNotStickerException(this.collector.getId(), this.album.getId(), sticker.getNumber()));
        hasSticker.unblockCopy();
    }

    public List<Sticker> getSpareStickers() {
        return ownedStickers.stream()
                .filter(hasSticker -> hasSticker.hasSpareUnblockedCopy())
                .map(HasSticker::getSticker)
                .toList();
    }

    public List<Sticker> getMissingStickers() {
        return album.getSections().stream()
                .flatMap(section -> section.getStickers().stream())
                .filter(sticker -> getStickerTotalCopies(sticker) == 0)
                .toList();
    }

    public ExchangeableStickers getExchangeableStickers(Collection otherCollection) {
        var missing1 = getMissingStickers();
        var spare1 = getSpareStickers();
        var missing2 = otherCollection.getMissingStickers();
        var spare2 = otherCollection.getSpareStickers();
        List<Sticker> giving = spare1.stream()
                .filter(sticker -> missing2.contains(sticker))
                .toList();
        List<Sticker> receiving = spare2.stream()
                .filter(sticker -> missing1.contains(sticker))
                .toList();
        return new ExchangeableStickers(giving, receiving);
    }
}


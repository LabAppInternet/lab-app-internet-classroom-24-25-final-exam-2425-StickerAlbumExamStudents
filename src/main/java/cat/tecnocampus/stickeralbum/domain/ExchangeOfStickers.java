package cat.tecnocampus.stickeralbum.domain;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.lang.Math.min;

@Entity
public class ExchangeOfStickers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="origin_collector_id", referencedColumnName="collector_id"),
            @JoinColumn(name="album_id", referencedColumnName="album_id")
    })    private final Collection origin;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="destination_collector_id", referencedColumnName="collector_id"),
            @JoinColumn(name="album_id2", referencedColumnName="album_id")
    })    private final Collection destination;

    @ManyToMany
    @JoinTable(
            name = "exchange_giving_stickers",
            joinColumns = @JoinColumn(name = "exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "sticker_id")
    )
    private final Set<Sticker> givingStickers;

    @ManyToMany
    @JoinTable(
            name = "exchange_receiving_stickers",
            joinColumns = @JoinColumn(name = "exchange_id"),
            inverseJoinColumns = @JoinColumn(name = "sticker_id")
    )
    private final Set<Sticker> receivingStickers;
    private final LocalDateTime proposalDate;
    private LocalDateTime resultDate;

    @Enumerated(EnumType.STRING)
    private ExchangeStatus status;

    public ExchangeOfStickers(Collection origin, Collection destination) {
        this.destination = destination;
        this.origin = origin;
        this.proposalDate = LocalDateTime.now();
        this.status = ExchangeStatus.PENDING;

        if (!origin.getAlbum().equals(destination.getAlbum()))
            throw new IllegalStateException("Stickers are not from the same album");

        ExchangeableStickers exchangeableStickers = origin.getExchangeableStickers(destination);
        int numberOfExchanges = min(exchangeableStickers.give().size(), exchangeableStickers.receive().size());

        //get the first numberOfExchanges stickers from each list
        List<Sticker> stickersToGive = exchangeableStickers.give().subList(0, numberOfExchanges);
        List<Sticker> stickersToReceive = exchangeableStickers.receive().subList(0, numberOfExchanges);
        //block the stickers
        stickersToGive.forEach(sticker -> origin.blockSticker(sticker));
        stickersToReceive.forEach(sticker -> destination.blockSticker(sticker));

        this.givingStickers = new HashSet<>(stickersToGive);
        this.receivingStickers = new HashSet<>(stickersToReceive);
    }

    public ExchangeOfStickers() {
        this.receivingStickers = new HashSet<>();
        this.givingStickers = new HashSet<>();
        this.origin = null;
        this.destination = null;
        this.proposalDate = null;
        this.status = ExchangeStatus.REJECTED;
    }

    public boolean isPossible() {
        return receivingStickers.size() > 0 && givingStickers.size() > 0;
    }

    @Transactional
    public void acceptExchange() {
        List<Sticker> givingStickersList = new ArrayList<>(givingStickers);
        List<Sticker> receivingStickersList = new ArrayList<>(receivingStickers);

        IntStream.range(0, givingStickersList.size())
                .forEach((i) -> singleExchange(givingStickersList.get(i), receivingStickersList.get(i)));
        status = ExchangeStatus.ACCEPTED;
        resultDate = LocalDateTime.now();
    }

    private void singleExchange(Sticker stickerOrigin, Sticker stickerDestination) {
        origin.removeBlockedSticker(stickerOrigin);
        destination.removeBlockedSticker(stickerDestination);
        origin.addSticker(stickerDestination, 1);
        destination.addSticker(stickerOrigin, 1);
    }

    public void rejectExchange() {
        givingStickers.forEach(sticker -> origin.unblockSticker(sticker));
        receivingStickers.forEach(sticker -> destination.unblockSticker(sticker));
        status = ExchangeStatus.REJECTED;
        resultDate = LocalDateTime.now();
    }

    public ExchangeStatus getStatus() {
        return status;
    }

    public Collector getDestinationUser() {
        return destination.getCollector();
    }

    public Collection getDestination() {
        return destination;
    }

    public Collection getOrigin() {
        return origin;
    }
}

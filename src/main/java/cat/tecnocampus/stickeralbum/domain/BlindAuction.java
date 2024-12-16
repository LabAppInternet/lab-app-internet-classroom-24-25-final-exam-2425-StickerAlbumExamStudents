package cat.tecnocampus.stickeralbum.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Optional;

@Entity
public class BlindAuction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double initialPrice;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Collector owner;
    @ManyToOne
    @JoinColumn(name = "sticker_id")
    private Sticker sticker;

    @OneToOne
    private Bid winningBid = null;
    private boolean winnerCalculated = false;

    private LocalDate beginDate;
    private LocalDate endDate;

    public BlindAuction(Collector owner, Sticker sticker, Double initialPrice, LocalDate beginDate, LocalDate endDate) {
        this.initialPrice = initialPrice;
        this.owner = owner;
        this.sticker = sticker;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public BlindAuction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Collector getOwner() {
        return owner;
    }

    public void setOwner(Collector owner) {
        this.owner = owner;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Optional<Bid> getWinningBid() {
        return Optional.ofNullable(winningBid);
    }

    public void setWinningBid(Bid winningBid) {
        this.winningBid = winningBid;
        winnerCalculated = true;
    }

    public boolean isFinished() {
        return winnerCalculated;
    }

    public boolean isOpen() {
        return (beginDate.isBefore(LocalDate.now()) || beginDate.isEqual(LocalDate.now())) && endDate.isAfter(LocalDate.now());
    }

    public boolean isPast() {
        return endDate.isBefore(LocalDate.now());
    }
}

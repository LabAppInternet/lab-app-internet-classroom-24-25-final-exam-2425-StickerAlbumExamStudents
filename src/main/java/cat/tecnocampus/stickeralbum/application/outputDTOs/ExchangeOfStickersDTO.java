package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.time.LocalDateTime;
import java.util.List;

public class ExchangeOfStickersDTO {
    private Long id;
    private Long albumId;
    private String albumName;
    private Long originCollectorId;
    private String originCollectorName;
    private Long destinationCollectorId;
    private String destinationCollectorName;
    private LocalDateTime proposalDate;
    private List<SitckerInExchangeDTO> give;
    private List<SitckerInExchangeDTO> receive;

    public ExchangeOfStickersDTO(Long id, Long albumId, String albumName, Long originCollectorId, String originCollectorName, Long destinationCollectorId, String destinationCollectorName, LocalDateTime proposalDate) {
        this.id = id;
        this.albumId = albumId;
        this.albumName = albumName;
        this.originCollectorId = originCollectorId;
        this.originCollectorName = originCollectorName;
        this.destinationCollectorId = destinationCollectorId;
        this.destinationCollectorName = destinationCollectorName;
        this.proposalDate = proposalDate;
    }

    public ExchangeOfStickersDTO(Long id, Long albumId, String albumName, Long originCollectorId, String originCollectorName, Long destinationCollectorId, String destinationCollectorName, LocalDateTime proposalDate, List<SitckerInExchangeDTO> give, List<SitckerInExchangeDTO> receive) {
        this.id = id;
        this.albumId = albumId;
        this.albumName = albumName;
        this.originCollectorId = originCollectorId;
        this.originCollectorName = originCollectorName;
        this.destinationCollectorId = destinationCollectorId;
        this.destinationCollectorName = destinationCollectorName;
        this.proposalDate = proposalDate;
        this.give = give;
        this.receive = receive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getOriginCollectorId() {
        return originCollectorId;
    }

    public void setOriginCollectorId(Long originCollectorId) {
        this.originCollectorId = originCollectorId;
    }

    public String getOriginCollectorName() {
        return originCollectorName;
    }

    public void setOriginCollectorName(String originCollectorName) {
        this.originCollectorName = originCollectorName;
    }

    public Long getDestinationCollectorId() {
        return destinationCollectorId;
    }

    public void setDestinationCollectorId(Long destinationCollectorId) {
        this.destinationCollectorId = destinationCollectorId;
    }

    public String getDestinationCollectorName() {
        return destinationCollectorName;
    }

    public void setDestinationCollectorName(String destinationCollectorName) {
        this.destinationCollectorName = destinationCollectorName;
    }

    public LocalDateTime getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(LocalDateTime proposalDate) {
        this.proposalDate = proposalDate;
    }

    public List<SitckerInExchangeDTO> getGive() {
        return give;
    }

    public void setGive(List<SitckerInExchangeDTO> give) {
        this.give = give;
    }

    public List<SitckerInExchangeDTO> getReceive() {
        return receive;
    }

    public void setReceive(List<SitckerInExchangeDTO> receive) {
        this.receive = receive;
    }
}
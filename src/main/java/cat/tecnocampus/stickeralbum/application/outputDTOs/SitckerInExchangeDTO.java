package cat.tecnocampus.stickeralbum.application.outputDTOs;

public class SitckerInExchangeDTO {
    private Long id;
    private Long number;
    private String name;

    public SitckerInExchangeDTO(Long id, Long number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}

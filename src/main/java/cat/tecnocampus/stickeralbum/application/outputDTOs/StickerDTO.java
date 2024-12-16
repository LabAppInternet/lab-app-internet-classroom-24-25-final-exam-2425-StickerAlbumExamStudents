package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.util.Map;

public class StickerDTO {
    private Long id;
    private Long number;
    private String name;
    private Map<String, String> description;

    public StickerDTO(Long id, String name, Map<String, String> description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }
}
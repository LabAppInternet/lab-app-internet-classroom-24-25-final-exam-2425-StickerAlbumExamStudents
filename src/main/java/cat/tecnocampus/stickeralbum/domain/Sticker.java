package cat.tecnocampus.stickeralbum.domain;

import jakarta.persistence.*;

import java.util.Map;

@Entity
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;
    private String name;

    @ElementCollection
    private Map<String, String> description;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sticker sticker = (Sticker) o;

        return id.equals(sticker.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

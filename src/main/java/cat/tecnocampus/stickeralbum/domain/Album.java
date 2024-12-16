package cat.tecnocampus.stickeralbum.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
public class Album {
    //jpa database generated value
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String editor;
    private LocalDate begins;
    private LocalDate ends;

    @ManyToOne()
    private Collector owner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "album_id")
    private List<Section> sections;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public LocalDate getBegins() {
        return begins;
    }

    public void setBegins(LocalDate begins) {
        this.begins = begins;
    }

    public LocalDate getEnds() {
        return ends;
    }

    public void setEnds(LocalDate ends) {
        this.ends = ends;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Collector getOwner() {
        return owner;
    }

    public void setOwner(Collector owner) {
        this.owner = owner;
    }

    public Optional<Sticker> getStickerWithNumber(Long stickerNUmber) {
        return sections.stream()
                .map(Section::getStickers)
                .flatMap(List::stream)
                .filter(sticker -> sticker.getNumber().equals(stickerNUmber))
                .findFirst();
    }

    public boolean isActive() {
        return (begins.isBefore(LocalDate.now()) || begins.equals(LocalDate.now())) &&
                (ends.isAfter(LocalDate.now()) || ends.equals(LocalDate.now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;
        return id.equals(album.id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + editor.hashCode();
        result = 31 * result + begins.hashCode();
        result = 31 * result + ends.hashCode();
        result = 31 * result + owner.hashCode();
        return result;
    }
}

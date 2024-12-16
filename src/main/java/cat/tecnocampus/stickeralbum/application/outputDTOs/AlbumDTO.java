package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.time.LocalDate;
import java.util.List;

public class AlbumDTO {

        private long id;
        private String name;
        private String editor;
        private LocalDate begins;
        private LocalDate ends;
        private List<SectionDTO>sections;

    public AlbumDTO(Long id, String name, String editor, LocalDate begins, LocalDate ends) {
        this.id = id;
        this.name = name;
        this.editor = editor;
        this.begins = begins;
        this.ends = ends;
    }

    public AlbumDTO(Long id, String name, String editor, LocalDate begins, LocalDate ends, List<SectionDTO> sections) {
        this.id = id;
        this.name = name;
        this.editor = editor;
        this.begins = begins;
        this.ends = ends;
        this.sections = sections;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<SectionDTO> getSections() {
        return sections;
    }

    public void setSections(List<SectionDTO> sections) {
        this.sections = sections;
    }
}

package cat.tecnocampus.stickeralbum.application.inputDTOs;

import java.time.LocalDate;
import java.util.List;

public record AlbumCommand(
        Long ownerId,
        String name,
        String editor,
        LocalDate begins,
        LocalDate ends,
        List<SectionCommand>sections
){
}

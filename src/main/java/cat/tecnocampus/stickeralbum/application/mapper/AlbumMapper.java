package cat.tecnocampus.stickeralbum.application.mapper;

import cat.tecnocampus.stickeralbum.application.inputDTOs.AlbumCommand;
import cat.tecnocampus.stickeralbum.application.inputDTOs.SectionCommand;
import cat.tecnocampus.stickeralbum.application.inputDTOs.StickerCommand;
import cat.tecnocampus.stickeralbum.application.outputDTOs.AlbumDTO;
import cat.tecnocampus.stickeralbum.application.outputDTOs.SectionDTO;
import cat.tecnocampus.stickeralbum.application.outputDTOs.StickerDTO;
import cat.tecnocampus.stickeralbum.domain.Album;
import cat.tecnocampus.stickeralbum.domain.Section;
import cat.tecnocampus.stickeralbum.domain.Sticker;

public class AlbumMapper {

    public static Album mapAlbumDTOtoDomain(AlbumCommand albumCommand) {
        Album album = new Album();
        album.setName(albumCommand.name());
        album.setEditor(albumCommand.editor());
        album.setBegins(albumCommand.begins());
        album.setEnds(albumCommand.ends());
        album.setSections(albumCommand.sections().stream().map(AlbumMapper::mapSectionDTOtoDomain).toList());
        return album;
    }

    public static Section mapSectionDTOtoDomain(SectionCommand sectionCommand) {
        Section section = new Section();
        section.setName(sectionCommand.name());
        section.setStickers(sectionCommand.stickers().stream().map(AlbumMapper::mapStickerDTOtoDomain).toList());
        return section;
    }

    public static Sticker mapStickerDTOtoDomain(StickerCommand stickerCommand) {
        Sticker sticker = new Sticker();
        sticker.setNumber(stickerCommand.number());
        sticker.setName(stickerCommand.name());
        sticker.setDescription(stickerCommand.description());
        return sticker;
    }

    public static AlbumDTO mapAlbumDomainToDTO(Album album) {
        //print sections name
        album.getSections().forEach(s -> System.out.println("section naem: " + s.getName()));

        return new AlbumDTO(
                album.getId(),
                album.getName(),
                album.getEditor(),
                album.getBegins(),
                album.getEnds(),
                album.getSections().stream().map(AlbumMapper::mapSectionDomainToDTO).toList()
        );
    }

    public static SectionDTO mapSectionDomainToDTO(Section section) {
        return new SectionDTO(
                section.getId(),
                section.getName(),
                section.getStickers().stream().map(AlbumMapper::mapStickerDomainToDTO).toList()
        );
    }

    public static StickerDTO mapStickerDomainToDTO(Sticker sticker) {
        return new StickerDTO(
                sticker.getNumber(),
                sticker.getName(),
                sticker.getDescription()
        );
    }
}

package cat.tecnocampus.stickeralbum.application.mapper;

import cat.tecnocampus.stickeralbum.application.outputDTOs.CollectedStickersAlbumDTO;
import cat.tecnocampus.stickeralbum.application.outputDTOs.HasStickerDTO;

import java.util.List;

public class CollectedAlbumSectionStickerMapper {

    // TODO 6.4 Given a list of HasStickerDTO, create a CollectedStickersAlbumDTO with the information of the stickers
    //   Since the list of HasStickerDTO is ordered by section and sticker number, your can go through the list and create
    //   a new CollectedStickersSectionDTO each time you find a new section. Then, you can add the stickers to the current section.
    public static CollectedStickersAlbumDTO hasStickersToCollectedAlbumSectionSticker(List<HasStickerDTO> stickers) {
        return null;
    }
}

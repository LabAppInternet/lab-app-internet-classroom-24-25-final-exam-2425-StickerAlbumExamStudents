package cat.tecnocampus.stickeralbum.persistence;

import cat.tecnocampus.stickeralbum.domain.HasSticker;
import cat.tecnocampus.stickeralbum.domain.HasStickerPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HasStickerRepository extends JpaRepository<HasSticker, HasStickerPK> {

    // TODO 6.3 get the stickers collected by the given collector in the given album. It should return a list of HasStickerDTO
    //  ordered by section and sticker number. Note that to get the information of the HasStickerDTO you will need to join the
    //  HasSticker, Collection, Album, Section and Sticker tables.
    //  Uncomment the following query and complete it
    //  Once you have completed the query, you can uncomment the test in TODO6_Tests
    //List<HasStickerDTO> findAllByCollectionOrderBySectionAndNumberAsc(Album album, Collector collector);

    // TODO 7.2 implement the query
    //List <CollectorStickersQuantityDTO> findCollectorsOfStickerInActiveAlbumsRanking();
}

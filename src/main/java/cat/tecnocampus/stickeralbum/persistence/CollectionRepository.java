package cat.tecnocampus.stickeralbum.persistence;

import cat.tecnocampus.stickeralbum.application.outputDTOs.CollectionDTO;
import cat.tecnocampus.stickeralbum.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, CollectionPK> {

    @Query("""
    select new cat.tecnocampus.stickeralbum.application.outputDTOs.CollectionDTO(
        c.album.id,
        c.album.name,
        c.collector.id,
        c.collector.name,
        size(c.ownedStickers),
        (select count(s) from c.album a join a.sections s join s.stickers)
        )
        from Collection c
        where c.collector.id = :userId
""")
    List<CollectionDTO> findAllByCollectorId(Long userId);

    @Query("""
    select new cat.tecnocampus.stickeralbum.application.outputDTOs.CollectionDTO(
        c.album.id,
        c.album.name,
        c.collector.id,
        c.collector.name,
        size(c.ownedStickers),
        (select count(s) from c.album a join a.sections s join s.stickers)
        )
        from Collection c
        where c.collector.id = :userId and c.album.begins <= CURRENT_DATE and c.album.ends >= CURRENT_DATE
""")
    List<CollectionDTO> findOpenByCollectionPKCollectorId(Long userId);

    @Query("""
        select c
        from Collection c
        join c.album.sections sec
        join sec.stickers stk
        where c.collector = :collector and stk = :sticker
    """)
    Optional<Collection> findByCollectorAndSticker(Collector collector, Sticker sticker);

    @Query("""
        select c
        from Collection c
        where c.collector = :collector and c.album = :album
    """)
    Optional<Collection> findByCollectorAndAlbum(Collector collector, Album album);
}

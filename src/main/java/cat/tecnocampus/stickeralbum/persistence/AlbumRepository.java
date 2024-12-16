package cat.tecnocampus.stickeralbum.persistence;

import cat.tecnocampus.stickeralbum.domain.Album;
import cat.tecnocampus.stickeralbum.domain.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    // get all the stickers of an album with a jpa query (not native)
    @Query("""
        SELECT stk
        FROM Album a
        JOIN a.sections s
        JOIN s.stickers stk
        WHERE a = :album
        """)
    public List<Sticker> findStickersByAlbum(Album album);

}

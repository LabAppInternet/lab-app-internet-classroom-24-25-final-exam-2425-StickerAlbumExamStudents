package cat.tecnocampus.stickeralbum.persistence;

import cat.tecnocampus.stickeralbum.domain.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

}

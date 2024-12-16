package cat.tecnocampus.stickeralbum.persistence;

import cat.tecnocampus.stickeralbum.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}

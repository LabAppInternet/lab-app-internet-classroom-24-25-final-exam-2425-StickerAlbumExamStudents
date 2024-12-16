package cat.tecnocampus.stickeralbum.persistence;

import cat.tecnocampus.stickeralbum.domain.Collector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectorRepository extends JpaRepository<Collector, Long> {
}

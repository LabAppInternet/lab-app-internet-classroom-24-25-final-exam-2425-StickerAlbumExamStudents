package cat.tecnocampus.stickeralbum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StickerAlbumApplication {

    public static void main(String[] args) {
        SpringApplication.run(StickerAlbumApplication.class, args);
    }

}

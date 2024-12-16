package cat.tecnocampus.stickeralbum.domain;

import java.util.List;

public record ExchangeableStickers(List<Sticker> give, List<Sticker> receive) {
    public ExchangeableStickers {
        if (give == null || receive == null) {
            throw new IllegalArgumentException("Give and receive lists must not be null");
        }
    }
}

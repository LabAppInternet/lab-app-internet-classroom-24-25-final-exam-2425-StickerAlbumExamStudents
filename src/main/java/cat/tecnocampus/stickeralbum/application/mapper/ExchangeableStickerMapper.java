package cat.tecnocampus.stickeralbum.application.mapper;

import cat.tecnocampus.stickeralbum.application.outputDTOs.ExchangeableStickersDTO;
import cat.tecnocampus.stickeralbum.application.outputDTOs.SitckerInExchangeDTO;
import cat.tecnocampus.stickeralbum.domain.ExchangeableStickers;

import java.util.stream.Collectors;

public class ExchangeableStickerMapper {
    public static ExchangeableStickersDTO mapExchangeableStickersDTO(ExchangeableStickers exchangeableStickers) {
        return new ExchangeableStickersDTO(
            exchangeableStickers.give().stream().map(i_stickerDTO -> new SitckerInExchangeDTO(i_stickerDTO.getId(), i_stickerDTO.getNumber(), i_stickerDTO.getName())).collect(Collectors.toList()),
            exchangeableStickers.receive().stream().map(i_stickerDTO -> new SitckerInExchangeDTO(i_stickerDTO.getId(), i_stickerDTO.getNumber(), i_stickerDTO.getName())).collect(Collectors.toList())
        );
    }
}

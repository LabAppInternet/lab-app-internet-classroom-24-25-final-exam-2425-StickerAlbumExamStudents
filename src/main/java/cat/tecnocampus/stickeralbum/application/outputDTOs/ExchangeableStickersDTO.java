package cat.tecnocampus.stickeralbum.application.outputDTOs;

import java.util.List;

public record ExchangeableStickersDTO(List<SitckerInExchangeDTO> give, List<SitckerInExchangeDTO> receive) {
}

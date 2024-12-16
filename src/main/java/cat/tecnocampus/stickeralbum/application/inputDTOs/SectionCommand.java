package cat.tecnocampus.stickeralbum.application.inputDTOs;

import java.util.List;

public record SectionCommand(String name, List<StickerCommand> stickers) {}
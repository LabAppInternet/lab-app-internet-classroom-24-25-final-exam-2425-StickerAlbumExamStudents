package cat.tecnocampus.stickeralbum.application.inputDTOs;

import java.util.Map;

public record StickerCommand(long number, String name, Map<String, String> description) {}
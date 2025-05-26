package hse.studying.filestoringservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Метаданные сохранённого файла")
public record FileInfoDto(
        @Schema(description = "ID в базе") Long id,
        @Schema(description = "Оригинальное имя") String filename
) {}
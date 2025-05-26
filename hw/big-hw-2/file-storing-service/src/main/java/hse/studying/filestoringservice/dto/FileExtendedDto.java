package hse.studying.filestoringservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Информация о файле с дубликатами")
public record FileExtendedDto(
        Long id,
        String filename,
        String contentHash,
        Long size,
        List<Long> duplicates
) {
}

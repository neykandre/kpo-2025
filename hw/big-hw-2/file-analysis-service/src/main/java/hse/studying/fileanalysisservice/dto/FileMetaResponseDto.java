package hse.studying.fileanalysisservice.dto;

import java.util.List;

public record FileMetaResponseDto(
        Long id,
        String filename,
        String contentHash,
        Long size,
        List<Long> duplicates
) {
}

package hse.studying.fileanalysisservice.dto;

import java.util.List;

public record AnalysisResponseDto(
        Long id,
        Long fileId,
        int paragraphs,
        int words,
        int chars,
        boolean duplicated,
        List<Long> duplicates) {
}
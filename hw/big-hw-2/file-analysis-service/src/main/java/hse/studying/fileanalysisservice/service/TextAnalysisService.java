package hse.studying.fileanalysisservice.service;

import hse.studying.fileanalysisservice.dto.AnalysisResponseDto;
import hse.studying.fileanalysisservice.dto.FileMetaResponseDto;
import hse.studying.fileanalysisservice.entity.AnalysisResult;
import hse.studying.fileanalysisservice.repository.AnalysisRepository;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TextAnalysisService {
    private final RestTemplate rest;
    private final AnalysisRepository repo;
    private final WordCloudService cloud;
    private final String fileServiceBase;

    public TextAnalysisService(
            RestTemplate rest,
            AnalysisRepository repo,
            WordCloudService cloud,
            @Value("${file-service.base-url}") String base) {
        this.rest = rest;
        this.repo = repo;
        this.cloud = cloud;
        this.fileServiceBase = base;
    }

    @Transactional
    public AnalysisResponseDto analyse(Long fileId, boolean generateCloud) {
        AnalysisResult ar = repo.findByFileId(fileId)
                .orElseGet(AnalysisResult::new);
        if (ar.getId() != null && (!generateCloud || ar.getWordCloudSvg() != null)) {
            return toDto(ar);
        }

        String url = fileServiceBase + "/files/" + fileId;
        ResponseEntity<byte[]> resp = rest.getForEntity(url, byte[].class);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("Cannot fetch file content");
        }
        String text = new String(Objects.requireNonNull(resp.getBody()), StandardCharsets.UTF_8);

        int paragraphs = (int) Arrays.stream(text.split("\r?\n\r?\n"))
                .filter(StringUtils::hasText)
                .count();
        int words = text.trim().isEmpty() ? 0 : text.split("\\s+").length;
        int chars = text.length();

        FileMetaResponseDto meta = rest.getForObject(
                fileServiceBase + "/files/" + fileId + "/extended",
                FileMetaResponseDto.class);

        if (meta == null) {
            throw new IllegalStateException("Cannot fetch file metadata");
        }
        Set<Long> dupIds = new HashSet<>(meta.duplicates());
        boolean anyDup = !dupIds.isEmpty();

        String cloudSvg = null;
        if (generateCloud) {
            cloudSvg = cloud.generate(text);
        }

        ar.setFileId(fileId);
        ar.setParagraphs(paragraphs);
        ar.setWords(words);
        ar.setChars(chars);
        ar.setDuplicated(anyDup);
        ar.setDuplicateFileIds(dupIds);
        ar.setWordCloudSvg(cloudSvg);
        repo.save(ar);

        return toDto(ar);
    }

    @Transactional(readOnly = true)
    public AnalysisResponseDto get(Long id) {
        AnalysisResult r = repo.findById(id).orElseThrow();
        return toDto(r);
    }

    @Transactional(readOnly = true)
    public AnalysisResult getEntity(Long id) {
        return repo.findById(id).orElseThrow();
    }

    private AnalysisResponseDto toDto(AnalysisResult r) {
        return new AnalysisResponseDto(
                r.getId(), r.getFileId(), r.getParagraphs(), r.getWords(), r.getChars(), r.isDuplicated(),
                new ArrayList<>(r.getDuplicateFileIds()));
    }
}
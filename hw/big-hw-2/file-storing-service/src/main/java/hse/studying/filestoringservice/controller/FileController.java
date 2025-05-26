package hse.studying.filestoringservice.controller;

import hse.studying.filestoringservice.dto.FileExtendedDto;
import hse.studying.filestoringservice.dto.FileInfoDto;
import hse.studying.filestoringservice.service.FileStoringService;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStoringService storage;

    @Operation(summary = "Загрузить .txt файл")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileInfoDto> upload(@RequestPart("file") MultipartFile file) throws IOException {
        FileInfoDto dto = storage.save(file);
        return ResponseEntity.status(201).body(dto);
    }

    @Operation(summary = "Метаданные")
    @GetMapping("/{id}/meta")
    public FileInfoDto meta(@PathVariable Long id) {
        return storage.getMeta(id);
    }

    @Operation(summary = "Расширенные метаданные")
    @GetMapping("/{id}/extended")
    public FileExtendedDto extended(@PathVariable Long id) {
        return storage.getExtendedMeta(id);
    }

    @Operation(summary = "Скачать файл")
    @GetMapping("/{id}")
    public ResponseEntity<FileSystemResource> download(@PathVariable Long id) throws IOException {
        Path path = storage.loadPath(id);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + path.getFileName() + '"')
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(Files.size(path))
                .body(new FileSystemResource(path));
    }
}

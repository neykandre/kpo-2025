package hse.studying.filestoringservice.service;

import hse.studying.filestoringservice.config.FileStoringProperties;
import hse.studying.filestoringservice.dto.FileExtendedDto;
import hse.studying.filestoringservice.dto.FileInfoDto;
import hse.studying.filestoringservice.entity.FileEntity;
import hse.studying.filestoringservice.repository.FileRepository;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileStoringService {

    private final FileRepository repo;
    private final FileStoringProperties props;
    private Path root;

    @PostConstruct
    public void init() throws IOException {
        root = Paths.get(props.location());
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
    }

    @Transactional
    public FileInfoDto save(MultipartFile multipart) throws IOException {
        if (multipart.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }

        String hash;
        try (InputStream in = multipart.getInputStream()) {
            hash = HashUtils.sha256Hex(in);
        }

        String newName = UUID.randomUUID() + ".txt";
        Path target = root.resolve(newName);
        Files.copy(multipart.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        FileEntity entity = FileEntity.builder()
                .filename(multipart.getOriginalFilename())
                .diskPath(target.toString())
                .sizeBytes(multipart.getSize())
                .contentHash(hash)
                .uploadedAt(Instant.now())
                .build();
        entity = repo.save(entity);
        return toDto(entity);
    }

    public FileInfoDto getMeta(Long id) {
        return repo.findById(id).map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("File %d not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    public FileExtendedDto getExtendedMeta(Long id) {
        FileEntity e = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("File %d not found".formatted(id)));

        List<Long> duplicates =
                repo.findAllByContentHash(e.getContentHash())
                        .stream()
                        .map(FileEntity::getId)
                        .filter(dupId -> !Objects.equals(dupId, id))
                        .toList();

        return new FileExtendedDto(
                e.getId(),
                e.getFilename(),
                e.getContentHash(),
                e.getSizeBytes(),
                duplicates
        );
    }

    public Path loadPath(Long id) {
        return repo.findById(id)
                .map(FileEntity::getDiskPath)
                .map(Path::of)
                .orElseThrow(() -> new IllegalArgumentException("File %d not found".formatted(id)));
    }

    private FileInfoDto toDto(FileEntity e) {
        return new FileInfoDto(e.getId(), e.getFilename());
    }
}

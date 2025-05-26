package hse.studying.filestoringservice.repository;

import hse.studying.filestoringservice.entity.FileEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findAllByContentHash(String contentHash);
}
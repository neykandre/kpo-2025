package hse.studying.fileanalysisservice.repository;

import hse.studying.fileanalysisservice.entity.AnalysisResult;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<AnalysisResult, Long> {
    Optional<AnalysisResult> findByFileId(Long fileId);
}
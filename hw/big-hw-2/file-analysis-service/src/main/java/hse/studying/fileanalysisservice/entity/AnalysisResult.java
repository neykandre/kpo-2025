package hse.studying.fileanalysisservice.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "analysis_results",
        uniqueConstraints = @UniqueConstraint(columnNames = "file_id")
)
public class AnalysisResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_id", nullable = false, unique = true)
    private Long fileId;
    private int paragraphs;
    private int words;
    private int chars;
    @Column(columnDefinition = "text")
    private String wordCloudSvg;
    private boolean duplicated;
    @ElementCollection
    @CollectionTable(name = "analysis_duplicates", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "duplicate_file_id")
    private Set<Long> duplicateFileIds = new HashSet<>();
    private Instant createdAt = Instant.now();
}
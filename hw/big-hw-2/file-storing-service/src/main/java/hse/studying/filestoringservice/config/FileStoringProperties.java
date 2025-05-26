package hse.studying.filestoringservice.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Путь к директории, куда складываются файлы на диске.
 */
@ConfigurationProperties(prefix = "file.storage")
@Validated
public record FileStoringProperties(@NotBlank String location) {}
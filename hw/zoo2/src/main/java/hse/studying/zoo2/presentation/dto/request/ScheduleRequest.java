package hse.studying.zoo2.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ScheduleRequest(
        @NotNull
        @Schema(description = "Animal ID to feed")
        UUID animalId,

        @NotNull
        @Schema(description = "Feeding time of day", example = "14:00:00")
        LocalTime time,

        @NotNull
        @Schema(description = "Type of food for feeding", example = "MEAT")
        String foodType
) {}

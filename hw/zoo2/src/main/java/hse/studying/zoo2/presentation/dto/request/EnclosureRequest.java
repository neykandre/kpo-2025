package hse.studying.zoo2.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record EnclosureRequest(
        @NotNull
        @Schema(description = "Type of the enclosure", example = "HERBIVORE")
        String typeName,

        @NotNull
        @Schema(description = "Area of the enclosure", example = "100")
        float area,

        @NotNull
        @Schema(description = "Supported diets: CARNIVORE, HERBIVORE, OMNIVORE", example = "[\"CARNIVORE\", \"HERBIVORE\"]")
        List<String> typeDiets,

        @Min(1)
        @Schema(description = "Maximum capacity of the enclosure")
        int capacity
) {}

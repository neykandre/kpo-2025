package hse.studying.zoo2.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record AnimalRequest(
        @NotNull
        @Schema(description = "Species of the animal")
        String speciesName,

        @NotNull
        @Schema(description = "Diet of the animal. Supported values: CARNIVORE, HERBIVORE, OMNIVORE", example = "HERBIVORE")
        String diet,

        @NotNull
        @Size(min = 1, max = 50)
        @Schema(description = "Nickname of the animal", example = "Leo")
        String nickname,

        @NotNull
        @Schema(description = "Date of birth of the animal", example = "2015-06-01")
        LocalDate birthDate,

        @NotNull
        @Schema(description = "Gender of the animal", example = "MALE")
        String gender,

        @NotNull
        @Schema(description = "Favorite food type of the animal. Supported values: MEAT, GRASS, MIXED_FEED", example = "MEAT")
        String favoriteFood
) {}

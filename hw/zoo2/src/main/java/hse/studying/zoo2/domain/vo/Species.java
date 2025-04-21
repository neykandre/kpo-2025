package hse.studying.zoo2.domain.vo;

import lombok.NonNull;

public record Species(@NonNull String name, @NonNull Diet diet) {
    public Species {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name blank");
        }
    }
}

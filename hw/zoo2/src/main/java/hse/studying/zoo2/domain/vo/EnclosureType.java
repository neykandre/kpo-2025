package hse.studying.zoo2.domain.vo;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import lombok.NonNull;

public record EnclosureType(@NonNull String name, @NonNull Set<Diet> supportedDiets) {
    public EnclosureType(String name, Set<Diet> supportedDiets) {
        if (supportedDiets.isEmpty()) {
            throw new IllegalArgumentException("supportedDiets empty");
        }
        this.name = name;
        this.supportedDiets = Collections.unmodifiableSet(EnumSet.copyOf(supportedDiets));
    }

    public boolean supports(Diet diet) {return supportedDiets.contains(diet);}
}
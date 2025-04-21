package hse.studying.zoo2.domain.models.ids;

import java.util.UUID;
import lombok.NonNull;

public record EnclosureID(@NonNull UUID value) {
    public static EnclosureID newID() {return new EnclosureID(UUID.randomUUID());}
}

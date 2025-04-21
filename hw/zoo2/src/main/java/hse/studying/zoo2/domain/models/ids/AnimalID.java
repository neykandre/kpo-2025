package hse.studying.zoo2.domain.models.ids;

import java.util.UUID;
import lombok.NonNull;

public record AnimalID(@NonNull UUID value) {
    public static AnimalID newID() {return new AnimalID(UUID.randomUUID());}
}

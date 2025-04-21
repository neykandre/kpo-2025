package hse.studying.zoo2.domain.models.ids;

import java.util.UUID;
import lombok.NonNull;

public record ScheduleID(@NonNull UUID value) {
    public static ScheduleID newID() {return new ScheduleID(UUID.randomUUID());}
}

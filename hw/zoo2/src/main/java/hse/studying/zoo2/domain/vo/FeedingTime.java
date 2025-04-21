package hse.studying.zoo2.domain.vo;

import java.time.LocalTime;
import lombok.NonNull;

public record FeedingTime(@NonNull LocalTime timeOfDay) {
}

package hse.studying.zoo2.domain.events;

import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.vo.FoodType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FeedingTimeEvent implements DomainEvent {
    private final AnimalID animalID;
    private final FoodType foodType;
    private final LocalDateTime scheduledTime;
    private final LocalDateTime occurredOn = LocalDateTime.now();
}

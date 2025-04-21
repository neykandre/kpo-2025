package hse.studying.zoo2.domain.events;

import hse.studying.zoo2.domain.models.ids.AnimalID;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class AnimalSickEvent implements DomainEvent {
    private final AnimalID animalID;
    private final LocalDateTime occurredOn = LocalDateTime.now();
}

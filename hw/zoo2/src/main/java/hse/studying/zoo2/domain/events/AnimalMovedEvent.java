package hse.studying.zoo2.domain.events;

import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class AnimalMovedEvent implements DomainEvent {
    private final AnimalID animalID;
    private final EnclosureID fromEnclosureID;
    private final EnclosureID toEnclosureID;
    private final LocalDateTime occurredOn = LocalDateTime.now();
}

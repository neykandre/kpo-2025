package hse.studying.zoo2.domain.models;

import hse.studying.zoo2.domain.events.AnimalMovedEvent;
import hse.studying.zoo2.domain.events.AnimalSickEvent;
import hse.studying.zoo2.domain.events.FeedingTimeEvent;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import hse.studying.zoo2.domain.utils.DomainEvents;
import hse.studying.zoo2.domain.vo.FoodType;
import hse.studying.zoo2.domain.vo.Gender;
import hse.studying.zoo2.domain.vo.Species;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Animal {
    private final AnimalID id = AnimalID.newID();
    private final Species species;
    private final String nickname;
    private final Gender gender;
    private final LocalDate birthDate;
    private final FoodType favoriteFood;
    private HealthStatus healthStatus;
    private EnclosureID enclosureID;

    public void feed(FoodType foodType) {
        DomainEvents.raise(new FeedingTimeEvent(id, foodType, LocalDateTime.now()));
    }

    public void heal() {
        healthStatus = HealthStatus.HEALTHY;
    }

    public void moveTo(EnclosureID enclosureID) {
        this.enclosureID = enclosureID;
        DomainEvents.raise(new AnimalMovedEvent(id, this.enclosureID, enclosureID));
    }

    public void feelSick() {
        healthStatus = HealthStatus.SICK;
        DomainEvents.raise(new AnimalSickEvent(id));
    }
}

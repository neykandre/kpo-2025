package hse.studying.zoo2.domain.models;

import hse.studying.zoo2.domain.events.FeedingTimeEvent;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.ScheduleID;
import hse.studying.zoo2.domain.utils.DomainEvents;
import hse.studying.zoo2.domain.vo.FeedingTime;
import hse.studying.zoo2.domain.vo.FoodType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class FeedingSchedule {
    private final ScheduleID id = ScheduleID.newID();
    private final AnimalID animalID;
    private FeedingTime feedingTime = null;
    private final FoodType foodType;
    private boolean done = false;

    public void reschedule(FeedingTime newFeedingTime) {
        this.feedingTime = newFeedingTime;
    }

    public void markAsDone() {
        if (this.done) {
            return;
        }
        this.done = true;
        LocalDateTime scheduled = LocalDateTime.of(LocalDate.now(), this.feedingTime.timeOfDay());
        DomainEvents.raise(new FeedingTimeEvent(this.animalID, this.foodType, scheduled));
    }

    public void reset() {
        this.done = false;
    }
}

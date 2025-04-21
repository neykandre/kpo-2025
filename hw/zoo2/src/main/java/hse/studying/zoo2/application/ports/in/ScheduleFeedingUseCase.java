package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.FeedingSchedule;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.ScheduleID;
import hse.studying.zoo2.domain.vo.FeedingTime;
import hse.studying.zoo2.domain.vo.FoodType;

/**
 * Use case: schedule a new feeding for an animal.
 */
public interface ScheduleFeedingUseCase {
    /**
     * Adds a new feeding schedule entry.
     *
     * @param animalId unique identifier of the animal
     * @param time     time of day when feeding should occur
     * @param foodType type of food for this feeding
     *
     * @return the newly created FeedingSchedule
     */
    FeedingSchedule scheduleFeeding(AnimalID animalId, FeedingTime time, FoodType foodType);

    /**
     * Reschedule an existing feeding schedule entry.
     *
     * @param scheduleId unique identifier of the schedule entry
     * @param time       time of day when feeding should occur
     *
     * @return the updated FeedingSchedule
     */
    FeedingSchedule rescheduleFeeding(ScheduleID scheduleId, FeedingTime time);

    /**
     * Resets all feedings to their initial state.
     */
    void resetFeedings();
}
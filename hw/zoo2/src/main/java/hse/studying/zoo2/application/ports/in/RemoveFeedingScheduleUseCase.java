package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.ids.ScheduleID;

/**
 * Use case: remove the feeding schedule from the zoo.
 */
public interface RemoveFeedingScheduleUseCase {
    /**
     * Remove the feeding schedule from the zoo.
     * @param scheduleID unique identifier of the schedule
     */
    void removeFeedingSchedule(ScheduleID scheduleID);
}

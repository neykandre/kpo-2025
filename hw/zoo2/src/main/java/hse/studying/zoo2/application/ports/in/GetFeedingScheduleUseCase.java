package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.FeedingSchedule;
import hse.studying.zoo2.domain.models.ids.ScheduleID;

/**
 * Use case: retrieve the feeding schedule of the zoo.
 */
public interface GetFeedingScheduleUseCase {
    /**
     * Returns the feeding schedule of the zoo
     *
     * @param scheduleID unique identifier of the schedule
     * @return the feeding schedule
     */
    FeedingSchedule getFeedingSchedule(ScheduleID scheduleID);
}

package hse.studying.zoo2.application.ports.out;

import hse.studying.zoo2.application.exceptions.EntityNotFoundException;
import hse.studying.zoo2.domain.models.FeedingSchedule;
import hse.studying.zoo2.domain.models.ids.ScheduleID;
import java.util.List;

/**
 * Repository interface for the FeedingSchedule aggregate.
 * <p>
 * Defines operations to manage FeedingSchedule entities,
 * including retrieval by id, listing, saving, and deletion.
 */
public interface FeedingScheduleRepository {
    /**
     * Retrieves a FeedingSchedule by its unique identifier.
     *
     * @param id unique identifier of the feeding schedule
     * @return the corresponding FeedingSchedule
     * @throws EntityNotFoundException if no schedule with the given id exists
     */
    FeedingSchedule load(ScheduleID id);

    /**
     * Retrieves all FeedingSchedule entities in the system.
     *
     * @return list of all feeding schedules
     */
    List<FeedingSchedule> loadAll();

    /**
     * Saves or updates the given FeedingSchedule in persistence.
     *
     * @param schedule the FeedingSchedule to save or update
     */
    void save(FeedingSchedule schedule);

    /**
     * Deletes the FeedingSchedule with the specified identifier.
     *
     * @param id unique identifier of the schedule to delete
     * @throws EntityNotFoundException if the schedule does not exist
     */
    void delete(ScheduleID id);
}

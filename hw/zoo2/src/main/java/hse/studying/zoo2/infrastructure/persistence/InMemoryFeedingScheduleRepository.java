package hse.studying.zoo2.infrastructure.persistence;

import hse.studying.zoo2.application.ports.out.FeedingScheduleRepository;
import hse.studying.zoo2.domain.models.FeedingSchedule;
import hse.studying.zoo2.domain.models.ids.ScheduleID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryFeedingScheduleRepository implements FeedingScheduleRepository {
    private final Map<ScheduleID, FeedingSchedule> feedings = new HashMap<>();

    @Override
    public void save(FeedingSchedule schedule) {
        feedings.put(schedule.getId(), schedule);
    }

    @Override
    public FeedingSchedule load(ScheduleID scheduleID) {
        return feedings.get(scheduleID);
    }

    @Override
    public List<FeedingSchedule> loadAll() {
        return new ArrayList<>(feedings.values());
    }

    @Override
    public void delete(ScheduleID scheduleID) {
        feedings.remove(scheduleID);
    }
}

package hse.studying.zoo2.application.services;

import hse.studying.zoo2.application.ports.in.FeedingAnimalsUseCase;
import hse.studying.zoo2.application.ports.in.GetFeedingScheduleUseCase;
import hse.studying.zoo2.application.ports.in.RemoveFeedingScheduleUseCase;
import hse.studying.zoo2.application.ports.in.ScheduleFeedingUseCase;
import hse.studying.zoo2.application.ports.out.AnimalRepository;
import hse.studying.zoo2.application.ports.out.FeedingScheduleRepository;
import hse.studying.zoo2.domain.models.FeedingSchedule;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.ScheduleID;
import hse.studying.zoo2.domain.vo.FeedingTime;
import hse.studying.zoo2.domain.vo.FoodType;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handling viewing and scheduling of feedings.
 */
@Service
@RequiredArgsConstructor
public class FeedingOrganizationService implements ScheduleFeedingUseCase, FeedingAnimalsUseCase,
        GetFeedingScheduleUseCase, RemoveFeedingScheduleUseCase {
    private final FeedingScheduleRepository feedingScheduleRepository;
    private final AnimalRepository animalRepository;

    @Override
    public FeedingSchedule scheduleFeeding(AnimalID animalId, FeedingTime time, FoodType foodType) {
        var schedule = new FeedingSchedule(animalId, foodType);
        schedule.reschedule(time);
        feedingScheduleRepository.save(schedule);
        return schedule;
    }

    @Override
    public FeedingSchedule rescheduleFeeding(ScheduleID scheduleID, FeedingTime time) {
        var schedule = feedingScheduleRepository.load(scheduleID);
        schedule.reschedule(time);
        feedingScheduleRepository.save(schedule);
        return schedule;
    }

    @Override
    public void resetFeedings() {
        var schedules = feedingScheduleRepository.loadAll();
        schedules.forEach(FeedingSchedule::reset);
        schedules.forEach(feedingScheduleRepository::save);
    }

    @Override
    public void feedAnimals() {
        var schedules = feedingScheduleRepository.loadAll();
        schedules.stream()
                .filter(feedingSchedule -> !feedingSchedule.isDone() &&
                        feedingSchedule.getFeedingTime().timeOfDay().isBefore(
                                LocalTime.now()))
                .forEach(schedule -> {
                    var animal = animalRepository.load(schedule.getAnimalID());
                    animal.feed(schedule.getFoodType());
                    animalRepository.save(animal);
                    schedule.markAsDone();
                });
    }

    @Override
    public FeedingSchedule getFeedingSchedule(ScheduleID scheduleID) {
        return feedingScheduleRepository.load(scheduleID);
    }

    @Override
    public void removeFeedingSchedule(ScheduleID scheduleID) {
        feedingScheduleRepository.delete(scheduleID);
    }
}

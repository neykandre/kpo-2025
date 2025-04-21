package hse.studying.zoo2.application.services;

import hse.studying.zoo2.application.ports.in.GetZooStatisticsUseCase;
import hse.studying.zoo2.application.ports.out.AnimalRepository;
import hse.studying.zoo2.application.ports.out.EnclosureRepository;
import hse.studying.zoo2.application.ports.out.FeedingScheduleRepository;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service that aggregates various metrics about the zoo.
 */
@Service
@RequiredArgsConstructor
public class ZooStatisticsService implements GetZooStatisticsUseCase {
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;
    private final FeedingScheduleRepository feedingScheduleRepository;

    @Override
    public int getTotalAnimals() {
        return animalRepository.loadAll().size();
    }

    @Override
    public int getTotalEnclosures() {
        return enclosureRepository.loadAll().size();
    }

    @Override
    public int getFreeEnclosures() {
        return (int) enclosureRepository.loadAll().stream()
                .filter(enclosure -> enclosure.getResidentCount() == 0)
                .count();
    }

    @Override
    public int getTotalUpcomingFeedings() {
        return (int) feedingScheduleRepository.loadAll().stream()
                .filter(feedingSchedule -> !feedingSchedule.isDone() &&
                        feedingSchedule.getFeedingTime().timeOfDay().isAfter(
                                LocalTime.now()))
                .count();
    }
}

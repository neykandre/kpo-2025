package hse.studying.zoo2.presentation.controllers;

import hse.studying.zoo2.application.ports.in.FeedingAnimalsUseCase;
import hse.studying.zoo2.application.ports.in.GetFeedingScheduleUseCase;
import hse.studying.zoo2.application.ports.in.RemoveFeedingScheduleUseCase;
import hse.studying.zoo2.application.ports.in.ScheduleFeedingUseCase;
import hse.studying.zoo2.application.services.FeedingOrganizationService;
import hse.studying.zoo2.domain.models.FeedingSchedule;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.ScheduleID;
import hse.studying.zoo2.domain.vo.FeedingTime;
import hse.studying.zoo2.domain.vo.FoodType;
import hse.studying.zoo2.presentation.dto.request.ScheduleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FeedingSchedules", description = "Operations related to feeding schedules")
@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class FeedingScheduleController {
    private final ScheduleFeedingUseCase scheduleFeedingUseCase;
    private final FeedingAnimalsUseCase feedingAnimalsUseCase;
    private final GetFeedingScheduleUseCase getFeedingScheduleUseCase;
    private final RemoveFeedingScheduleUseCase removeFeedingScheduleUseCase;

    @Operation(summary = "Schedule Feeding")
    @PostMapping
    public ResponseEntity<FeedingSchedule> scheduleFeeding(@Valid @RequestBody ScheduleRequest request) {
        var schedule = scheduleFeedingUseCase.scheduleFeeding(new AnimalID(request.animalId()), new FeedingTime(request.time()),
                FoodType.valueOf(request.foodType()));
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }

    @Operation(summary = "Get Feeding Schedule")
    @GetMapping("/{id}")
    public ResponseEntity<FeedingSchedule> getFeedingSchedule(@PathVariable UUID id) {
        return ResponseEntity.ok(getFeedingScheduleUseCase.getFeedingSchedule(new ScheduleID(id)));
    }

    @Operation(summary = "Feed Animals")
    @PostMapping("/feed")
    public ResponseEntity<Void> feedAnimals() {
        feedingAnimalsUseCase.feedAnimals();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove Feeding Schedule")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFeedingSchedule(@PathVariable UUID id) {
        removeFeedingScheduleUseCase.removeFeedingSchedule(new ScheduleID(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reschedule Feeding")
    @PostMapping("/{id}/reschedule")
    public ResponseEntity<FeedingSchedule> rescheduleFeeding(@PathVariable UUID id, @Valid @RequestBody ScheduleRequest request) {
        var schedule = scheduleFeedingUseCase.rescheduleFeeding(new ScheduleID(id), new FeedingTime(request.time()));
        return ResponseEntity.ok(schedule);
    }

    @Operation(summary = "Reset Feedings")
    @PostMapping("/reset")
    public ResponseEntity<Void> resetFeedings() {
        scheduleFeedingUseCase.resetFeedings();
        return ResponseEntity.noContent().build();
    }
}

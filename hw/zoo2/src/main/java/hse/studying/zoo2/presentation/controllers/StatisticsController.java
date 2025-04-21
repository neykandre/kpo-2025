package hse.studying.zoo2.presentation.controllers;

import hse.studying.zoo2.application.ports.in.GetZooStatisticsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Statistics", description = "Statistics about zoo")
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final GetZooStatisticsUseCase getZooStatisticsUseCase;

    @Operation(summary = "Get total animals")
    @PostMapping("/total-animals")
    public int getTotalAnimals() {
        return getZooStatisticsUseCase.getTotalAnimals();
    }

    @Operation(summary = "Get total enclosures")
    @PostMapping("/total-enclosures")
    public int getTotalEnclosures() {
        return getZooStatisticsUseCase.getTotalEnclosures();
    }

    @Operation(summary = "Get free enclosures")
    @PostMapping("/free-enclosures")
    public int getFreeEnclosures() {
        return getZooStatisticsUseCase.getFreeEnclosures();
    }

    @Operation(summary = "Get upcoming feedings")
    @PostMapping("/upcoming-feedings")
    public int getTotalFeedings() {
        return getZooStatisticsUseCase.getTotalUpcomingFeedings();
    }
}

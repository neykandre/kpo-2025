package hse.studying.zoo2.presentation.controllers;

import hse.studying.zoo2.application.ports.in.AddAnimalUseCase;
import hse.studying.zoo2.application.ports.in.ControlAnimalHealthUseCase;
import hse.studying.zoo2.application.ports.in.GetAnimalUseCase;
import hse.studying.zoo2.application.ports.in.RemoveAnimalUseCase;
import hse.studying.zoo2.application.services.AnimalTransferService;
import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import hse.studying.zoo2.domain.vo.Diet;
import hse.studying.zoo2.domain.vo.FoodType;
import hse.studying.zoo2.domain.vo.Gender;
import hse.studying.zoo2.domain.vo.Species;
import hse.studying.zoo2.presentation.dto.request.AnimalRequest;
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

@Tag(name = "Animals", description = "Operations related to animals")
@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {
    private final AddAnimalUseCase addAnimalUseCase;
    private final RemoveAnimalUseCase removeAnimalUseCase;
    private final GetAnimalUseCase getAnimalUseCase;
    private final AnimalTransferService animalTransferService;
    private final ControlAnimalHealthUseCase controlAnimalHealthUseCase;

    @Operation(summary = "Add an animal to the zoo")
    @PostMapping
    public ResponseEntity<Animal> addAnimal(@Valid @RequestBody AnimalRequest request) {
        Diet diet = Diet.valueOf(request.diet());
        var species = new Species(request.speciesName(), diet);
        var animal = addAnimalUseCase.addAnimal(
                species,
                request.nickname(),
                request.birthDate(),
                Gender.valueOf(request.gender()),
                FoodType.valueOf(request.favoriteFood())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(animal);
    }

    @Operation(summary = "Get an animal by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable UUID id) {
        return ResponseEntity.ok(getAnimalUseCase.getAnimal(new AnimalID(id)));
    }

    @Operation(summary = "Remove an animal from the zoo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAnimal(@PathVariable UUID id) {
        removeAnimalUseCase.removeAnimal(new AnimalID(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Transfer an animal to another enclosure")
    @PostMapping("/{id}/transfer/{newEnclosureId}")
    public ResponseEntity<Animal> transferAnimal(@PathVariable UUID id, @PathVariable UUID newEnclosureId) {
        return ResponseEntity.ok(animalTransferService.transfer(new AnimalID(id), new EnclosureID(newEnclosureId)));
    }

    @Operation(summary = "Feel sick")
    @PostMapping("/{id}/sick")
    public ResponseEntity<Animal> feelSick(@PathVariable UUID id) {
        return ResponseEntity.ok(controlAnimalHealthUseCase.feelSick(new AnimalID(id)));
    }

    @Operation(summary = "Heal")
    @PostMapping("/{id}/heal")
    public ResponseEntity<Animal> heal(@PathVariable UUID id) {
        return ResponseEntity.ok(controlAnimalHealthUseCase.heal(new AnimalID(id)));
    }
}

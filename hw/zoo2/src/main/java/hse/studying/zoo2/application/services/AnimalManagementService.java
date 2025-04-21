package hse.studying.zoo2.application.services;

import hse.studying.zoo2.application.ports.in.AddAnimalUseCase;
import hse.studying.zoo2.application.ports.in.ControlAnimalHealthUseCase;
import hse.studying.zoo2.application.ports.in.GetAnimalUseCase;
import hse.studying.zoo2.application.ports.in.RemoveAnimalUseCase;
import hse.studying.zoo2.application.ports.out.AnimalRepository;
import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.vo.FoodType;
import hse.studying.zoo2.domain.vo.Gender;
import hse.studying.zoo2.domain.vo.Species;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnimalManagementService implements AddAnimalUseCase, RemoveAnimalUseCase, GetAnimalUseCase,
        ControlAnimalHealthUseCase {
    private final AnimalRepository animalRepository;

    @Override
    public Animal addAnimal(Species species, String nickname, LocalDate birthDate, Gender gender, FoodType favoriteFood) {
        var animal = new Animal(species, nickname, gender, birthDate, favoriteFood);
        animalRepository.save(animal);
        return animal;
    }

    @Override
    public void removeAnimal(AnimalID id) {
        animalRepository.delete(id);
    }

    @Override
    public Animal getAnimal(AnimalID id) {
        return animalRepository.load(id);
    }

    @Override
    public Animal feelSick(AnimalID id) {
        var animal = animalRepository.load(id);
        animal.feelSick();
        animalRepository.save(animal);
        return animal;
    }

    @Override
    public Animal heal(AnimalID id) {
        var animal = animalRepository.load(id);
        animal.heal();
        animalRepository.save(animal);
        return animal;
    }
}

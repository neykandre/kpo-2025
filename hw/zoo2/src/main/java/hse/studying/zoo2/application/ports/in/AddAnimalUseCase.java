package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.vo.FoodType;
import hse.studying.zoo2.domain.vo.Gender;
import hse.studying.zoo2.domain.vo.Species;
import java.time.LocalDate;

/**
 * Use case: add a new animal to the zoo.
 */
public interface AddAnimalUseCase {
    /**
     * Registers a new animal with the given attributes.
     *
     * @param species      species of the animal (scientific name and diet)
     * @param nickname     nickname of the animal
     * @param birthDate    date of birth of the animal
     * @param gender       gender of the animal
     * @param favoriteFood favorite food type of the animal
     *
     * @return the newly created animal
     */
    Animal addAnimal(
            Species species,
            String nickname,
            LocalDate birthDate,
            Gender gender,
            FoodType favoriteFood);
}

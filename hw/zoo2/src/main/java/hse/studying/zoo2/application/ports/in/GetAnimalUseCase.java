package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.models.ids.AnimalID;

/**
 * Use case: retrieve an existing animal from the zoo.
 */
public interface GetAnimalUseCase {
    /**
     * Retrieves the animal identified by the given ID.
     *
     * @param id unique identifier of the animal
     * @return the animal
     */
    Animal getAnimal(AnimalID id);
}

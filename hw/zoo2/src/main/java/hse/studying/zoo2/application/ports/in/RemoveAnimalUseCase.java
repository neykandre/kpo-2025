package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.ids.AnimalID;

/**
 * Use case: remove an existing animal from the zoo.
 */
public interface RemoveAnimalUseCase {
    /**
     * Deletes the animal identified by the given ID.
     *
     * @param id unique identifier of the animal to remove
     */
    void removeAnimal(AnimalID id);
}
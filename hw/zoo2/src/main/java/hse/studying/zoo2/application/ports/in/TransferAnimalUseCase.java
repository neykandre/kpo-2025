package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.EnclosureID;

/**
 * Use case: transfer an animal from one enclosure to another.
 */
public interface TransferAnimalUseCase {
    /**
     * Moves the animal with given ID to the target enclosure.
     *
     * @param animalId    unique identifier of the animal to transfer
     * @param toEnclosure unique identifier of the destination enclosure
     *
     * @return the transferred animal
     */
    Animal transfer(AnimalID animalId, EnclosureID toEnclosure);
}
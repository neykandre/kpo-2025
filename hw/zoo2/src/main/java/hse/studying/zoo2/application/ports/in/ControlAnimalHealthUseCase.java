package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.models.ids.AnimalID;

/**
 * Use case: control the health of an animal.
 */
public interface ControlAnimalHealthUseCase {
    /**
     * Feels sick
     *
     * @param id unique identifier of the animal
     * @return the animal
     */
    public Animal feelSick(AnimalID id);

    /**
     * Heals the animal
     *
     * @param id unique identifier of the animal
     * @return the animal
     */
    public Animal heal(AnimalID id);
}

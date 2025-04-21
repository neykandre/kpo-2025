package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.Enclosure;
import hse.studying.zoo2.domain.vo.EnclosureType;

/**
 * Use case: add a new enclosure to the zoo.
 */
public interface AddEnclosureUseCase {
    /**
     * Registers a new enclosure with specified type and capacity.
     *
     * @param type     type of the enclosure (carnivore, herbivore, etc.)
     * @param area     area of the enclosure
     * @param capacity maximum number of animals the enclosure can hold
     *
     * @return the newly created enclosure
     */
    Enclosure addEnclosure(EnclosureType type, float area, int capacity);
}

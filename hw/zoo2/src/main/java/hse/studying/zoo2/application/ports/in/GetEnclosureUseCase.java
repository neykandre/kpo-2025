package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.Enclosure;
import hse.studying.zoo2.domain.models.ids.EnclosureID;

/**
 * Use case: retrieve an existing enclosure from the zoo.
 */
public interface GetEnclosureUseCase {
    /**
     * Retrieves the enclosure identified by the given ID.
     *
     * @param id unique identifier of the enclosure
     * @return the corresponding enclosure
     */
    Enclosure getEnclosure(EnclosureID id);
}

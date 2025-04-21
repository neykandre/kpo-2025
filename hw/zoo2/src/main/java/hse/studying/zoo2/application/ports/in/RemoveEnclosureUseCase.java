package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.ids.EnclosureID;

/**
 * Use case: remove an existing enclosure from the zoo.
 */
public interface RemoveEnclosureUseCase {
    /**
     * Deletes the enclosure identified by the given ID.
     *
     * @param id unique identifier of the enclosure to remove
     */
    void removeEnclosure(EnclosureID id);
}
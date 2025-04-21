package hse.studying.zoo2.application.ports.in;

import hse.studying.zoo2.domain.models.ids.EnclosureID;

/**
 * Use case: clean an enclosure.
 */
public interface CleanEnclosureUseCase {
    /**
     * Cleans the enclosure identified by the given ID.
     *
     * @param id unique identifier of the enclosure to clean
     */
    void cleanEnclosure(EnclosureID id);
}

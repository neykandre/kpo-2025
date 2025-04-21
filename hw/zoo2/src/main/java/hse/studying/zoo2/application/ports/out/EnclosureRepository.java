package hse.studying.zoo2.application.ports.out;

import hse.studying.zoo2.application.exceptions.EntityNotFoundException;
import hse.studying.zoo2.domain.models.Enclosure;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import java.util.List;

/**
 * Repository interface for the Enclosure aggregate.
 * <p>
 * Defines operations to manage Enclosure entities,
 * including retrieval by id, listing, saving, and deletion.
 */
public interface EnclosureRepository {
    /**
     * Retrieves an Enclosure by its unique identifier.
     *
     * @param id unique identifier of the enclosure
     * @return the corresponding Enclosure
     * @throws EntityNotFoundException if no enclosure with the given id exists
     */
    Enclosure load(EnclosureID id);

    /**
     * Retrieves all Enclosure entities in the system.
     *
     * @return list of all enclosures
     */
    List<Enclosure> loadAll();

    /**
     * Saves or updates the given Enclosure in persistence.
     *
     * @param enclosure the Enclosure to save or update
     */
    void save(Enclosure enclosure);

    /**
     * Deletes the Enclosure with the specified identifier.
     *
     * @param id unique identifier of the enclosure to delete
     * @throws EntityNotFoundException if the enclosure does not exist
     */
    void delete(EnclosureID id);
}

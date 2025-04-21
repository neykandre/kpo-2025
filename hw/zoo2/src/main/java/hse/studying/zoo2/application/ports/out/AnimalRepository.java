package hse.studying.zoo2.application.ports.out;

import hse.studying.zoo2.application.exceptions.EntityNotFoundException;
import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import java.util.List;

/**
 * Repository interface for Animal aggregate.
 * Combines loading, saving, deleting, and listing operations.
 */
public interface AnimalRepository {
    /**
     * Fetches an Animal by its unique identifier.
     *
     * @param id the unique identifier of the Animal
     * @return the Animal entity
     * @throws EntityNotFoundException if not found
     */
    Animal load(AnimalID id);

    /**
     * Returns all animals in the zoo.
     *
     * @return list of all Animal entities
     */
    List<Animal> loadAll();

    /**
     * Persists the Animal (insert or update).
     *
     * @param animal the entity to save
     */
    void save(Animal animal);

    /**
     * Deletes the Animal by its identifier.
     *
     * @param id the unique identifier of the Animal
     */
    void delete(AnimalID id);
}

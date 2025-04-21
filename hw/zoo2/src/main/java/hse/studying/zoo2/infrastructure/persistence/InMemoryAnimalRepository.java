package hse.studying.zoo2.infrastructure.persistence;

import hse.studying.zoo2.application.ports.out.AnimalRepository;
import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAnimalRepository implements AnimalRepository {
    private final Map<AnimalID, Animal> animals = new HashMap<>();

    @Override
    public void save(Animal animal) {
        animals.put(animal.getId(), animal);
    }

    @Override
    public Animal load(AnimalID id) {
        return animals.get(id);
    }

    @Override
    public List<Animal> loadAll() {
        return new ArrayList<>(animals.values());
    }

    @Override
    public void delete(AnimalID id) {
        animals.remove(id);
    }
}

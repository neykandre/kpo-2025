package hse.studying.zoo2.infrastructure.persistence;

import hse.studying.zoo2.application.ports.out.EnclosureRepository;
import hse.studying.zoo2.domain.models.Enclosure;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEnclosureRepository implements EnclosureRepository {
    private final Map<EnclosureID, Enclosure> enclosures = new HashMap<>();

    @Override
    public Enclosure load(EnclosureID id) {
        return enclosures.get(id);
    }

    @Override
    public List<Enclosure> loadAll() {
        return new ArrayList<>(enclosures.values());
    }

    @Override
    public void save(Enclosure enclosure) {
        enclosures.put(enclosure.getId(), enclosure);
    }

    @Override
    public void delete(EnclosureID id) {
        enclosures.remove(id);
    }
}

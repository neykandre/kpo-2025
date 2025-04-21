package hse.studying.zoo2.domain.models;

import hse.studying.zoo2.domain.events.CleaningEvent;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import hse.studying.zoo2.domain.utils.DomainEvents;
import hse.studying.zoo2.domain.vo.EnclosureType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Enclosure {
    private final EnclosureID id = EnclosureID.newID();
    private final EnclosureType type;
    private final float area;
    private final int capacity;
    private final List<AnimalID> residents = new ArrayList<>();

    public int getResidentCount() {return residents.size();}

    public boolean canAccept(Animal animal) {
        return residents.size() < capacity && type.supports(animal.getSpecies().diet());
    }

    public void addAnimal(Animal animal) {
        if (!canAccept(animal)) {
            throw new IllegalArgumentException("Animal cannot be added to enclosure");
        }
        residents.add(animal.getId());
    }

    public void removeAnimal(Animal animal) {
        residents.remove(animal.getId());
    }

    public void clean() {
        DomainEvents.raise(new CleaningEvent(id));
    }
}

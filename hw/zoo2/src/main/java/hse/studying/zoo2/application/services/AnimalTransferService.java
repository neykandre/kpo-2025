package hse.studying.zoo2.application.services;

import hse.studying.zoo2.application.ports.in.TransferAnimalUseCase;
import hse.studying.zoo2.application.ports.out.AnimalRepository;
import hse.studying.zoo2.application.ports.out.EnclosureRepository;
import hse.studying.zoo2.domain.models.Animal;
import hse.studying.zoo2.domain.models.ids.AnimalID;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handling transfer of animals between enclosures.
 */
@Service
@RequiredArgsConstructor
public class AnimalTransferService implements TransferAnimalUseCase {
    private final AnimalRepository animalRepository;
    private final EnclosureRepository enclosureRepository;

    @Override
    public Animal transfer(AnimalID animalID, EnclosureID enclosureID) {
        var animal = animalRepository.load(animalID);
        var enclosure = enclosureRepository.load(enclosureID);
        enclosure.addAnimal(animal);
        animal.moveTo(enclosureID);
        animalRepository.save(animal);
        enclosureRepository.save(enclosure);
        return animal;
    }
}

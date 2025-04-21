package hse.studying.zoo2.application.services;

import hse.studying.zoo2.application.ports.in.AddEnclosureUseCase;
import hse.studying.zoo2.application.ports.in.CleanEnclosureUseCase;
import hse.studying.zoo2.application.ports.in.GetEnclosureUseCase;
import hse.studying.zoo2.application.ports.in.RemoveEnclosureUseCase;
import hse.studying.zoo2.application.ports.out.EnclosureRepository;
import hse.studying.zoo2.domain.models.Enclosure;
import hse.studying.zoo2.domain.models.ids.EnclosureID;
import hse.studying.zoo2.domain.vo.EnclosureType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service handling creation and deletion of Enclosure aggregates.
 */
@Service
@RequiredArgsConstructor
public class EnclosureManagementService implements AddEnclosureUseCase, RemoveEnclosureUseCase, GetEnclosureUseCase,
        CleanEnclosureUseCase {
    private final EnclosureRepository enclosureRepository;

    @Override
    public Enclosure addEnclosure(EnclosureType type, float area, int capacity) {
        var enclosure = new Enclosure(type, area, capacity);
        enclosureRepository.save(enclosure);
        return enclosure;
    }

    @Override
    public void removeEnclosure(EnclosureID id) {
        enclosureRepository.delete(id);
    }

    @Override
    public Enclosure getEnclosure(EnclosureID id) {
        return enclosureRepository.load(id);
    }

    @Override
    public void cleanEnclosure(EnclosureID id) {
        var enclosure = enclosureRepository.load(id);
        enclosure.clean();
        enclosureRepository.save(enclosure);
    }
}

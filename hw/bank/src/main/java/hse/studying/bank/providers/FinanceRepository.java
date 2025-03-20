package hse.studying.bank.providers;

import hse.studying.bank.interfaces.Identifiable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public class FinanceRepository<T extends Identifiable<Long>> implements CrudRepository<T, Long> {

    private final CrudRepository<T, Long> realRepo;
    private final Map<Long, T> cache = new HashMap<>();

    public FinanceRepository(CrudRepository<T, Long> realRepo) {
        this.realRepo = realRepo;
        realRepo.findAll().forEach(entity -> cache.put(entity.getId(), entity));
    }

    @Override
    public <S extends T> S save(S entity) {
        realRepo.save(entity);
        cache.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        realRepo.saveAll(entities);
        entities.forEach(entity -> cache.put(entity.getId(), entity));
        return entities;
    }

    @Override
    public Optional<T> findById(Long id) {
        if (cache.containsKey(id)) {
            return Optional.of(cache.get(id));
        }
        T entity = realRepo.findById(id).orElse(null);
        if (entity != null) {
            cache.put(id, entity);
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public boolean existsById(Long id) {
        if (cache.containsKey(id)) {
            return true;
        }
        T entity = realRepo.findById(id).orElse(null);
        if (entity != null) {
            cache.put(id, entity);
        }
        return entity != null;
    }

    @Override
    public Iterable<T> findAll() {
        realRepo.findAll().forEach(entity -> cache.put(entity.getId(), entity));
        return new ArrayList<>(cache.values());
    }

    @Override
    public Iterable<T> findAllById(Iterable<Long> ids) {
        List<T> result = new ArrayList<>();
        ids.forEach(id -> findById(id).ifPresent(result::add));
        return result;
    }

    @Override
    public long count() {
        return cache.size();
    }

    @Override
    public void deleteById(Long id) {
        cache.remove(id);
        realRepo.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        cache.remove(entity.getId());
        realRepo.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        cache.clear();
        realRepo.deleteAll();
    }
}

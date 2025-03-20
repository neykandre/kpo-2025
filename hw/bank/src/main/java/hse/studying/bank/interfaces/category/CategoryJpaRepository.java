package hse.studying.bank.interfaces.category;

import hse.studying.bank.domains.category.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends CrudRepository<Category, Long> {

}

package hse.studying.bank.interfaces.operation;

import hse.studying.bank.domains.operation.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationJpaRepository extends CrudRepository<Operation, Long> {

}

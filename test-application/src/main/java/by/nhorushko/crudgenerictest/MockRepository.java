package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgenerictest.domain.entity.MockEntity;
import org.springframework.data.repository.CrudRepository;

public interface MockRepository extends CrudRepository<MockEntity, Long> {
}

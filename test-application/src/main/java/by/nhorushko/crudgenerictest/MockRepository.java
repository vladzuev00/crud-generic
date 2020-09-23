package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.springframework.data.repository.CrudRepository;

public interface MockRepository extends CrudRepository<MockAEntity, Long> {
}

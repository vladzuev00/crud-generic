package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgenerictest.domain.entity.MockBEntity;
import org.springframework.data.repository.CrudRepository;

public interface MockBRepository  extends CrudRepository<MockBEntity, Long> {
}

package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgenerictest.domain.entity.MockBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MockBRepository  extends JpaRepository<MockBEntity, Long>, JpaSpecificationExecutor<MockBEntity> {
}

package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MockRepository extends JpaRepository<MockAEntity, Long>, JpaSpecificationExecutor<MockAEntity> {
}

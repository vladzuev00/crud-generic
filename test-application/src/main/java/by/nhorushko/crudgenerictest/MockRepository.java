package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MockRepository extends PagingAndSortingRepository<MockAEntity, Long>, JpaSpecificationExecutor<MockAEntity> {
}

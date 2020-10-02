package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgenerictest.domain.entity.MockBEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MockBRepository  extends PagingAndSortingRepository<MockBEntity, Long>, JpaSpecificationExecutor<MockBEntity> {
}

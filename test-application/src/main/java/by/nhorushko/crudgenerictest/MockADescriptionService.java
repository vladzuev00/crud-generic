package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.service.PartialDtoGenericService;
import by.nhorushko.crudgenerictest.domain.dto.MockADescription;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.springframework.stereotype.Service;

@Service
public class MockADescriptionService extends PartialDtoGenericService<MockADescription, MockAEntity> {
    public MockADescriptionService(MockService immutableGenericService) {
        super(MockADescription.class, MockAEntity.class, immutableGenericService);
    }
}

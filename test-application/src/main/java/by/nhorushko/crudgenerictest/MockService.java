package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.service.CrudGenericService;
import by.nhorushko.crudgenerictest.domain.dto.MockDto;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.springframework.stereotype.Service;

@Service
public class MockService extends CrudGenericService<MockDto, MockAEntity, MockRepository, MockAMapper> {
    public MockService(MockRepository repository, MockAMapper mapper) {
        super(repository, mapper, MockDto.class, MockAEntity.class);
    }
}

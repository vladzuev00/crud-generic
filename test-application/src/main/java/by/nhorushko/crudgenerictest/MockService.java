package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.service.CrudGenericService;
import by.nhorushko.crudgenerictest.domain.dto.MockADto;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.springframework.stereotype.Service;

@Service
public class MockService extends CrudGenericService<MockADto, MockAEntity, MockRepository, MockAMapper> {
    public MockService(MockRepository repository, MockAMapper mapper) {
        super(repository, mapper, MockADto.class, MockAEntity.class);
    }
}

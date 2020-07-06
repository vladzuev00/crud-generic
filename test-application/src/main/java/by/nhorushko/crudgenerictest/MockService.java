package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.service.CrudGenericService;
import by.nhorushko.crudgenerictest.domain.dto.MockDto;
import by.nhorushko.crudgenerictest.domain.entity.MockEntity;
import org.springframework.stereotype.Service;

@Service
public class MockService extends CrudGenericService<MockDto, MockEntity, MockRepository, MockMapper> {
    public MockService(MockRepository repository, MockMapper mapper) {
        super(repository, mapper, MockDto.class, MockEntity.class);
    }
}

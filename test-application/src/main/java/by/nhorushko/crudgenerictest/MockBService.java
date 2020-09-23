package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.service.CrudGenericService;
import by.nhorushko.crudgenerictest.domain.dto.MockBDto;
import by.nhorushko.crudgenerictest.domain.entity.MockBEntity;
import by.nhorushko.crudgenerictest.mockmapper.MockBMapper;
import org.springframework.stereotype.Service;

@Service
public class MockBService  extends CrudGenericService<MockBDto, MockBEntity, MockBRepository, MockBMapper> {
    public MockBService(MockBRepository repository, MockBMapper mapper) {
        super(repository, mapper, MockBDto.class, MockBEntity.class);
    }
}

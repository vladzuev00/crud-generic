package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import by.nhorushko.crudgenerictest.domain.dto.MockDto;
import by.nhorushko.crudgenerictest.domain.entity.MockEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MockMapper extends AbstractMapper<MockEntity, MockDto> {

    public MockMapper(ModelMapper modelMapper) {
        super(MockEntity.class, MockDto.class, modelMapper);
    }
}

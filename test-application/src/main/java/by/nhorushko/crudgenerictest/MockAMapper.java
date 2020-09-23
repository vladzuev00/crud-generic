package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import by.nhorushko.crudgenerictest.domain.dto.MockDto;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MockAMapper extends AbstractMapper<MockAEntity, MockDto> {

    public MockAMapper(ModelMapper modelMapper) {
        super(MockAEntity.class, MockDto.class, modelMapper);
    }
}

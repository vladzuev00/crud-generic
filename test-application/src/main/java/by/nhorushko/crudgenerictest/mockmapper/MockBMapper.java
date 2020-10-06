package by.nhorushko.crudgenerictest.mockmapper;

import by.nhorushko.crudgeneric.mapper.ImmutableDtoAbstractMapper;
import by.nhorushko.crudgenerictest.domain.dto.MockBDto;
import by.nhorushko.crudgenerictest.domain.entity.MockBEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MockBMapper extends ImmutableDtoAbstractMapper<MockBEntity, MockBDto> {

    private final MockAMapper mockAMapper;

    public MockBMapper(ModelMapper modelMapper, MockAMapper mockAMapper) {
        super(MockBEntity.class, MockBDto.class, modelMapper);
        this.mockAMapper = mockAMapper;
    }

    @Override
    protected MockBDto mapDto(MockBEntity e) {
        return new MockBDto(e.getId(), e.getName(), mockAMapper.toDto(e.getMockAEntity()));
    }
}

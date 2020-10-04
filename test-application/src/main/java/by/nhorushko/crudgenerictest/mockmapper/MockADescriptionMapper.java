package by.nhorushko.crudgenerictest.mockmapper;

import by.nhorushko.crudgeneric.mapper.ImmutableDtoAbstractMapper;
import by.nhorushko.crudgenerictest.MockAMapper;
import by.nhorushko.crudgenerictest.domain.dto.MockADescription;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MockADescriptionMapper extends ImmutableDtoAbstractMapper<MockAEntity, MockADescription> {

    public MockADescriptionMapper(ModelMapper modelMapper) {
        super(MockAEntity.class, MockADescription.class, modelMapper);
    }

    @Override
    protected MockADescription mapDto(MockAEntity e) {
        return new MockADescription(e.getId(), e.getDescription());
    }
}

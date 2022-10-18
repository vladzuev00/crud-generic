package by.nhorushko.crudgeneric.mapper;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import org.modelmapper.ModelMapper;

@Deprecated
public abstract class ImmutableDtoAbstractMapper<ENTITY extends AbstractEntity, DTO extends AbstractDto> extends AbstractMapper<ENTITY, DTO> {

    public ImmutableDtoAbstractMapper(Class<ENTITY> entityClass, Class<DTO> dtoClass, ModelMapper modelMapper) {
        super(entityClass, dtoClass, modelMapper);
    }

    @Override
    protected abstract DTO mapDto(ENTITY entity);
}

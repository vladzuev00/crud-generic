package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;

public abstract class AbsMapperDto<ENTITY extends AbstractEntity<?>, DTO extends AbstractDto<?>>
        extends AbsMapperVo<ENTITY, DTO> {

    public AbsMapperDto(ModelMapper modelMapper, Class<ENTITY> entityClass, Class<DTO> dtoClass) {
        super(modelMapper, entityClass, dtoClass);
    }

    public DTO map(ENTITY entity) {
        return super.map(entity);
    }

    public List<DTO> map(Collection<ENTITY> entities) {
        return super.map(entities);
    }

    @Override
    protected abstract DTO create(ENTITY from);
}

package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public abstract class Mapper<ENTITY extends AbstractEntity<?>, DTO extends AbstractDto<?>> {
    protected final ModelMapper modelMapper;
    protected final Class<ENTITY> entityClass;
    protected final Class<DTO> dtoClass;

    public Mapper(ModelMapper modelMapper, Class<ENTITY> entityClass, Class<DTO> dtoClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.configureMapper();
    }

    public DTO toDto(ENTITY entity) {
        return !Objects.isNull(entity)
                ? this.modelMapper.map(entity, this.dtoClass)
                : null;
    }

    public List<DTO> toDto(Collection<ENTITY> entities) {
        return !Objects.isNull(entities)
                ? entities.stream()
                .map(this::toDto)
                .collect(toList())
                : null;
    }

    protected abstract DTO createDto(ENTITY entity);

    @SuppressWarnings("unchecked")
    private void configureMapper() {
        this.modelMapper.createTypeMap(this.entityClass, this.dtoClass)
                .setPostConverter(this.createConverterEntityToDto())
                .setProvider(request -> this.createDto((ENTITY) request.getSource()));
    }

    private Converter<ENTITY, DTO> createConverterEntityToDto() {
        return context -> {
            final ENTITY source = context.getSource();
            return context.getDestination();
        };
    }
}

package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public abstract class Mapper<ENTITY extends AbstractEntity, DTO extends AbstractDto> {
    protected final ModelMapper modelMapper;
    protected final Class<ENTITY> entityClass;
    private final Class<DTO> dtoClass;

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

    public ENTITY toEntity(DTO dto) {
        return !Objects.isNull(dto)
                ? this.modelMapper.map(dto, this.entityClass)
                : null;
    }

    public List<ENTITY> toEntity(Collection<DTO> dtos) {
        return !Objects.isNull(dtos)
                ? dtos.stream()
                .map(this::toEntity)
                .collect(toList())
                : null;
    }

    protected abstract DTO createDto(ENTITY entity);

    protected void mapSpecificFields(ENTITY source, DTO destination) {

    }

    protected void mapSpecificFields(DTO source, ENTITY destination) {

    }

    @SuppressWarnings("unchecked")
    private void configureMapper() {
        this.modelMapper.createTypeMap(this.entityClass, this.dtoClass)
                .setPostConverter(this.createConverterEntityToDto())
                .setProvider(request -> this.createDto((ENTITY) request.getSource()));

        this.modelMapper.createTypeMap(this.dtoClass, this.entityClass)
                .setPostConverter(this.createConverterDtoToEntity());
    }

    private Converter<ENTITY, DTO> createConverterEntityToDto() {
        return context -> {
            final ENTITY source = context.getSource();
            final DTO destination = context.getDestination();
            this.mapSpecificFields(source, destination);
            return destination;
        };
    }

    private Converter<DTO, ENTITY> createConverterDtoToEntity() {
        return context -> {
            final DTO source = context.getSource();
            final ENTITY destination = context.getDestination();
            this.mapSpecificFields(source, destination);
            return destination;
        };
    }
}

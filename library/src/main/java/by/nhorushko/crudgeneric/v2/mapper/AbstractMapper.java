package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;

import static by.nhorushko.crudgeneric.util.CollectionUtil.mapToList;

/**
 * Mapping for objects without Id
 * https://stackoverflow.com/questions/44534172/how-to-customize-modelmapper
 */
public abstract class AbstractMapper<DTO extends AbstractDto<?>, ENTITY extends AbstractEntity<?>> {
    private final ModelMapper modelMapper;
    private final Class<DTO> dtoType;
    private final Class<ENTITY> entityType;

    public AbstractMapper(final ModelMapper modelMapper, final Class<DTO> dtoType, final Class<ENTITY> entityType) {
        this.modelMapper = modelMapper;
        this.dtoType = dtoType;
        this.entityType = entityType;
        this.configureMapping();
    }

    public final ENTITY mapToEntity(final DTO dto) {
        return this.mapNullable(dto, this.entityType);
    }

    public final List<ENTITY> mapToEntities(final Collection<DTO> dtos) {
        return this.mapNullable(dtos, this.entityType);
    }

    public final DTO mapToDto(final ENTITY entity) {
        return this.mapNullable(entity, this.dtoType);
    }

    public final List<DTO> mapToDtos(final Collection<ENTITY> entities) {
        return this.mapNullable(entities, this.dtoType);
    }

    protected abstract DTO createDto(final ENTITY entity);

    protected abstract void mapSpecificFields(final ENTITY entity, final DTO dto);

    private <R> R mapNullable(final Object source, final Class<R> resultType) {
        return source != null ? this.modelMapper.map(source, resultType) : null;
    }

    private <R> List<R> mapNullable(final Collection<?> sources, final Class<R> resultType) {
        return sources != null ? mapToList(sources, source -> this.mapNullable(source, resultType)) : null;
    }

    private void configureMapping() {
        this.configureMappingEntityToDto();
        this.configureMappingEntityToDto();
    }

    private void configureMappingEntityToDto() {

    }

    private void configureMappingDtoToEntity() {

    }
}

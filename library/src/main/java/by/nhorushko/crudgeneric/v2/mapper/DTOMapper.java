package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public abstract class DTOMapper<EntityType extends AbstractEntity, DTOType extends AbstractDto> {
    protected final ModelMapper modelMapper;
    private final Class<EntityType> entityType;
    private final Class<DTOType> dtoType;

    @SuppressWarnings("unchecked")
    public DTOMapper(ModelMapper modelMapper, Class<EntityType> entityType, Class<DTOType> dtoType) {
        this.modelMapper = modelMapper;
        this.entityType = entityType;
        this.dtoType = dtoType;

        final TypeMap<EntityType, DTOType> entityToDtoTypeMap = modelMapper.typeMap(this.entityType, this.dtoType);
        entityToDtoTypeMap.setProvider(request -> {
            final EntityType entity = (EntityType) request.getSource();
            return DTOMapper.this.mapDTO(entity);
        });
    }

    public final DTOType map(EntityType entity) {
        return this.modelMapper.map(entity, this.dtoType);
    }

    public final EntityType map(DTOType dto) {
        final EntityType entity = this.modelMapper.map(dto, this.entityType);
        this.mapSpecificFieldsToEntity(entity, dto);
        return entity;
    }

    protected abstract DTOType mapDTO(EntityType entity);

    protected void mapSpecificFieldsToEntity(EntityType entity, DTOType dto) {

    }
}

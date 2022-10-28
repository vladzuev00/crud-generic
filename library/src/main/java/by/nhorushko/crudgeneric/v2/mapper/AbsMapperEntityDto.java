package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;

public abstract class AbsMapperEntityDto<ENTITY extends AbstractEntity<?>, DTO extends AbstractDto<?>>
        extends AbsMapperDto<ENTITY, DTO> {

    public AbsMapperEntityDto(ModelMapper modelMapper, Class<ENTITY> entityClass, Class<DTO> dtoClass) {
        super(modelMapper, entityClass, dtoClass);
        this.configureMapper();
    }

    public ENTITY toEntity(DTO dto) {
        return map(dto, entityClass);
    }

    public List<ENTITY> toEntities(Collection<DTO> dtos) {
        return mapAll(dtos, entityClass);
    }

    protected void mapSpecificFields(DTO source, ENTITY destination) {

    }

    private void configureMapper() {
        modelMapper.createTypeMap(dtoClass, entityClass)
                .setPostConverter(createConverterDtoToEntity());
    }

    private Converter<DTO, ENTITY> createConverterDtoToEntity() {
        return context -> {
            DTO source = context.getSource();
            ENTITY destination = context.getDestination();
            mapSpecificFields(source, destination);
            return destination;
        };
    }
}

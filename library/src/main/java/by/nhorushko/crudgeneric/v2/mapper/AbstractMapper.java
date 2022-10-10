package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public abstract class AbstractMapper<ENTITY extends AbstractEntity<?>, DTO extends AbstractDto<?>>
        extends Mapper<ENTITY, DTO> {

    public AbstractMapper(ModelMapper modelMapper, Class<ENTITY> entityClass, Class<DTO> dtoClass) {
        super(modelMapper, entityClass, dtoClass);
        this.configureMapper();
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

    protected void mapSpecificFields(DTO source, ENTITY destination) {

    }

    private void configureMapper() {
        this.modelMapper.createTypeMap(super.dtoClass, super.entityClass)
                .setPostConverter(this.createConverterDtoToEntity());
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

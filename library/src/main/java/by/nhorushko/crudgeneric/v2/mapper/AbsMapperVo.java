package by.nhorushko.crudgeneric.v2.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Мапинг классов без ID которые не являются ENTITY
 */
public abstract class AbsMapperVo<FROM, TO> {

    protected final ModelMapper modelMapper;
    protected final Class<FROM> entityClass;
    protected final Class<TO> dtoClass;

    public AbsMapperVo(ModelMapper modelMapper, Class<FROM> entityClass, Class<TO> dtoClass) {
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.configureMapper();
    }

    public TO map(FROM from) {
        return !Objects.isNull(from)
                ? mapAny(from, this.dtoClass)
                : null;
    }

    public List<TO> map(Collection<FROM> entities) {
        return !Objects.isNull(entities)
                ? entities.stream()
                .map(this::map)
                .collect(toList())
                : null;
    }

    public <T> T mapAny(Object obj, Class<T> clazz){
        return this.modelMapper.map(obj, clazz);
    }

    protected abstract TO create(FROM entity);

    protected void mapSpecificFields(FROM source, TO destination) {

    }

    @SuppressWarnings("unchecked")
    private void configureMapper() {
        this.modelMapper.createTypeMap(this.entityClass, this.dtoClass)
                .setPostConverter(this.createConverter())
                .setProvider(request -> this.create((FROM) request.getSource()));
    }

    private Converter<FROM, TO> createConverter() {
        return context -> {
            final FROM source = context.getSource();
            final TO destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }
}
package by.nhorushko.crudgeneric.v2.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Мапинг классов без ID которые не являются ENTITY
 */
public abstract class AbsMapperVo<FROM, TO> {

    protected final ModelMapper modelMapper;
    protected final Class<FROM> fromClass;
    protected final Class<TO> toClass;

    public AbsMapperVo(ModelMapper modelMapper, Class<FROM> fromClass, Class<TO> toClass) {
        this.modelMapper = modelMapper;
        this.fromClass = fromClass;
        this.toClass = toClass;
        this.configureMapper();
    }

    public TO map(FROM from) {
        return !Objects.isNull(from)
                ? mapAny(from, this.toClass)
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

    public <T>List<T> mapAny(Collection<?> objects, Class<T> clazz) {
        return objects.stream()
                .map(o -> mapAny(o, clazz))
                .collect(toList());
    }

    protected abstract TO create(FROM from);

    @SuppressWarnings("unchecked")
    private void configureMapper() {
        this.modelMapper
                .createTypeMap(this.fromClass, this.toClass)
                .setConverter(MappingContext::getDestination)
                .setProvider(request -> this.create((FROM) request.getSource()));
    }
}
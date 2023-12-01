package by.nhorushko.crudgeneric.v2.mapper;

import org.modelmapper.ModelMapper;

import java.util.List;

import static by.nhorushko.crudgeneric.util.CollectionUtil.mapToList;

/**
 * Mapping for objects without Id
 * https://stackoverflow.com/questions/44534172/how-to-customize-modelmapper
 */
public abstract class AbsMapperBase<SOURCE, RESULT> {
    private final ModelMapper modelMapper;
    private final Class<SOURCE> sourceClass;
    private final Class<RESULT> resultClass;

    public AbsMapperBase(final ModelMapper modelMapper,
                         final Class<SOURCE> sourceClass,
                         final Class<RESULT> resultClass) {
        this.modelMapper = modelMapper;
        this.sourceClass = sourceClass;
        this.resultClass = resultClass;
    }

    public final RESULT map(final SOURCE source) {
        return source != null ? this.modelMapper.map(source, this.resultClass) : null;
    }

    public final List<RESULT> map(final List<SOURCE> sources) {
        return sources != null ? mapToList(sources, this::map) : null;
    }
}

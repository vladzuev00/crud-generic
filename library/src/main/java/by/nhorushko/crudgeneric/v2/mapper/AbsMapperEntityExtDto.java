package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;

import static java.util.Objects.isNull;

public abstract class AbsMapperEntityExtDto<ENTITY extends AbstractEntity<?>, DTO extends AbstractDto<?>, EXT_ID,
        EXT extends AbstractEntity<EXT_ID>>
        extends AbsMapperEntityDto<ENTITY, DTO> {
    private final EntityManager entityManager;
    private final Class<EXT> extClass;

    public AbsMapperEntityExtDto(ModelMapper modelMapper, Class<ENTITY> entityClass, Class<DTO> dtoClass,
                                 EntityManager entityManager, Class<EXT> extClass) {
        super(modelMapper, entityClass, dtoClass);
        this.entityManager = entityManager;
        this.extClass = extClass;
    }

    public ENTITY revMap(EXT_ID relationId, DTO dto) {
        if (isNull(dto)) {
            return null;
        }
        final ENTITY entity = super.modelMapper.map(dto, super.fromClass);
        this.setRelation(relationId, entity);
        return entity;
    }

    protected abstract void setRelation(EXT ext, ENTITY destination);

    private void setRelation(EXT_ID relationId, ENTITY destination) {
        final EXT EXT = this.entityManager
                .getReference(this.extClass, relationId);
        this.setRelation(EXT, destination);
    }
}

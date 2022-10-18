package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;

import static java.util.Objects.isNull;

public abstract class ExtDtoEntityMapper<ENTITY extends AbstractEntity<?>, DTO extends AbstractDto<?>, RELATION_ID,
        RELATION extends AbstractEntity<RELATION_ID>>
        extends DtoEntityMapper<ENTITY, DTO> {
    private final EntityManager entityManager;
    private final Class<RELATION> relationClass;

    public ExtDtoEntityMapper(ModelMapper modelMapper, Class<ENTITY> entityClass, Class<DTO> dtoClass,
                              EntityManager entityManager, Class<RELATION> relationClass) {
        super(modelMapper, entityClass, dtoClass);
        this.entityManager = entityManager;
        this.relationClass = relationClass;
    }

    public ENTITY revMap(RELATION_ID relationId, DTO dto) {
        if (isNull(dto)) {
            return null;
        }
        final ENTITY entity = super.modelMapper.map(dto, super.entityClass);
        this.setRelation(relationId, entity);
        return entity;
    }

    protected abstract void setRelation(RELATION relation, ENTITY destination);

    private void setRelation(RELATION_ID relationId, ENTITY destination) {
        final RELATION relation = this.entityManager
                .getReference(this.relationClass, relationId);
        this.setRelation(relation, destination);
    }
}

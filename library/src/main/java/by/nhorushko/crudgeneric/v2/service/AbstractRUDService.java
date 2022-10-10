package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.util.FieldCopyUtil;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbstractMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public abstract class AbstractRUDService<
        ENTITY_ID,
        ENTITY extends AbstractEntity<ENTITY_ID>,
        DTO extends AbstractDto<ENTITY_ID>,
        MAPPER extends AbstractMapper<ENTITY, DTO>>

        extends AbstractReadService<ENTITY_ID, ENTITY, DTO, MAPPER> {
    private static final Set<String> IGNORE_PARTIAL_UPDATE_PROPERTIES = Set.of("id");

    public AbstractRUDService(MAPPER mapper, JpaRepository<ENTITY, ENTITY_ID> repository) {
        super(mapper, repository);
    }

    public DTO update(DTO dto) {
        if (this.isIdNotDefined(dto.getId())) {
            throw new AppNotFoundException("Impossible to update entity, because of not defined id.");
        }
        final ENTITY entity = super.mapper.toEntity(dto);
        final ENTITY updatedEntity = super.repository.save(entity);
        return this.mapper.toDto(updatedEntity);
    }

    public DTO updatePartial(ENTITY_ID id, Object source) {
        if (this.isIdNotDefined(id)) {
            throw new AppNotFoundException("Impossible to update entity, because of not defined id.");
        }
        final Optional<ENTITY> optionalEntity = super.repository.findById(id);
        final ENTITY entity = optionalEntity.orElseThrow(() -> new AppNotFoundException("Impossible to do partial "
                + "update to entity with not existing id = '" + id + "'."));
        FieldCopyUtil.copy(source, entity, IGNORE_PARTIAL_UPDATE_PROPERTIES);
        return this.mapper.toDto(entity);
    }

    public void delete(ENTITY_ID id) {
        if (this.isIdNotDefined(id)) {
            throw new AppNotFoundException("Impossible to delete entity with id: " + id
                    + ", because of not defined id.");
        }
        super.repository.deleteById(id);
    }

    private boolean isIdNotDefined(ENTITY_ID id) {
        if (id == null) {
            return true;
        }
        if (id instanceof Number) {
            return ((Number) id).longValue() == 0;
        }
        return false;
    }
}

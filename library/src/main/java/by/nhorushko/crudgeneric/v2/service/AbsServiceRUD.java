package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.util.FieldCopyUtil;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.domain.IdEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

import static java.lang.String.format;

/**
 * Read Update Delete service
 */
public abstract class AbsServiceRUD<
        ENTITY_ID,
        ENTITY extends AbstractEntity<ENTITY_ID>,
        DTO extends AbstractDto<ENTITY_ID>,
        MAPPER extends AbsMapperEntityDto<ENTITY, DTO>,
        REPOSITORY extends JpaRepository<ENTITY, ENTITY_ID>>

        extends AbsServiceR<ENTITY_ID, ENTITY, DTO, MAPPER, REPOSITORY> {

    protected Set<String> IGNORE_PARTIAL_UPDATE_PROPERTIES = Set.of("id");

    public AbsServiceRUD(MAPPER mapper, REPOSITORY repository) {
        super(mapper, repository);
    }

    public DTO update(DTO dto) {
        checkId(dto);
        ENTITY entity = repository.save(mapper.toEntity(dto));
        return mapper.toDto(entity);
    }

    private void checkId(IdEntity<ENTITY_ID> entity) {
        if (entity.isNew()) {
            throw new IllegalArgumentException(
                    format("Updated entity: %s should have id: (not null OR 0), but was id: %s", entity.getClass(), entity.getId()));
        }
    }

    public DTO updatePartial(ENTITY_ID id, Object source) {
        ENTITY entity = repository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(
                        format("Partial update operation is impossible because of not existing entity with id = '%s'.",
                                id)));
        FieldCopyUtil.copy(source, entity, IGNORE_PARTIAL_UPDATE_PROPERTIES);
        return this.mapper.toDto(entity);
    }

    public void delete(ENTITY_ID id) {
        repository.deleteById(id);
    }
}

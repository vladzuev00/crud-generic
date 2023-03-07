package by.nhorushko.crudgeneric.v2.service;

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
        return runUpdate(dto);
    }

    public DTO updatePartial(ENTITY_ID id, Object partial) {
        DTO target = copyPartial(id, partial);
        return runUpdate(target);
    }

    private DTO runUpdate(DTO dto) {
        checkId(dto);
        ENTITY prevValue = repository.getOne(dto.getId());
        ENTITY newValue = mapper.toEntity(dto);
        preUpdate(prevValue, newValue);
        ENTITY actual = repository.save(newValue);
        return mapper.toDto(actual);
    }

    /**
     * Здесь можно внести изменения в newValue, на основании предыдущего значения
     * @param prevValue
     * @param newValue
     */
    protected void preUpdate(ENTITY prevValue, ENTITY newValue) {

    }

    private void checkId(IdEntity<ENTITY_ID> entity) {
        if (entity.isNew()) {
            throw new IllegalArgumentException(
                    format("Updated entity: %s should have id: (not null OR 0), but was id: %s", entity.getClass(), entity.getId()));
        }
    }

    private DTO copyPartial(ENTITY_ID id, Object source) {
        DTO target = getById(id);
        FieldCopyUtil.copy(source, target, IGNORE_PARTIAL_UPDATE_PROPERTIES);
        return target;
    }

    public void delete(ENTITY_ID id) {
        repository.deleteById(id);
    }
}

package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperExtDtoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *
 * Read Update Delete Create Service
 */
public abstract class AbsServiceExtCRUD<
        ENTITY_ID,
        ENTITY extends AbstractEntity<ENTITY_ID>,
        DTO extends AbstractDto<ENTITY_ID>,
        EXT_ID,
        EXT extends AbstractEntity<EXT_ID>,
        REPOSITORY extends JpaRepository<ENTITY, ENTITY_ID>>

        extends AbsServiceRUD<ENTITY_ID, ENTITY, DTO, AbsMapperExtDtoEntity<ENTITY, DTO, EXT_ID, EXT>, REPOSITORY> {

    public AbsServiceExtCRUD(AbsMapperExtDtoEntity<ENTITY, DTO, EXT_ID, EXT> mapper,
                             REPOSITORY repository) {
        super(mapper, repository);
    }

    public DTO save(EXT_ID relationId, DTO dto) {
        final ENTITY entity = super.mapper.revMap(relationId, dto);
        final ENTITY savedEntity = super.repository.save(entity);
        return super.mapper.map(savedEntity);
    }

    public List<DTO> saveAll(EXT_ID relationId, Collection<DTO> dtos) {
        final List<ENTITY> entities = dtos.stream()
                .map(dto -> super.mapper.revMap(relationId, dto))
                .collect(toList());
        final List<ENTITY> savedEntities = super.repository.saveAll(entities);
        return super.mapper.map(savedEntities);
    }
}

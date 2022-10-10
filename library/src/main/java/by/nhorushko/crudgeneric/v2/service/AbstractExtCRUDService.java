package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.ExtAbstractMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class AbstractExtCRUDService<
        ENTITY_ID,
        ENTITY extends AbstractEntity<ENTITY_ID>,
        DTO extends AbstractDto<ENTITY_ID>,
        RELATION_ID,
        RELATION extends AbstractEntity<RELATION_ID>>

        extends AbstractRUDService<ENTITY_ID, ENTITY, DTO, ExtAbstractMapper<ENTITY, DTO, RELATION_ID, RELATION>> {

    public AbstractExtCRUDService(ExtAbstractMapper<ENTITY, DTO, RELATION_ID, RELATION> mapper,
                                  JpaRepository<ENTITY, ENTITY_ID> repository) {
        super(mapper, repository);
    }

    public DTO save(RELATION_ID relationId, DTO dto) {
        final ENTITY entity = super.mapper.toEntity(relationId, dto);
        final ENTITY savedEntity = super.repository.save(entity);
        return super.mapper.toDto(savedEntity);
    }

    public List<DTO> saveAll(RELATION_ID relationId, Collection<DTO> dtos) {
        final List<ENTITY> entities = dtos.stream()
                .map(dto -> super.mapper.toEntity(relationId, dto))
                .collect(toList());
        final List<ENTITY> savedEntities = super.repository.saveAll(entities);
        return savedEntities.stream()
                .map(super.mapper::toDto)
                .collect(toList());
    }
}

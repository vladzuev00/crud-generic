package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.ExtAbstractMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional
public abstract class AbstractExtCRUDService<
        ENTITY_ID,
        ENTITY extends AbstractEntity<ENTITY_ID>,
        DTO extends AbstractDto<ENTITY_ID>,
        RELATION_ID,
        RELATION extends AbstractEntity<RELATION_ID>,
        REPOSITORY extends JpaRepository<ENTITY, ENTITY_ID>>

        extends AbstractRUDService<ENTITY_ID, ENTITY, DTO, ExtAbstractMapper<ENTITY, DTO, RELATION_ID, RELATION>, REPOSITORY> {

    public AbstractExtCRUDService(ExtAbstractMapper<ENTITY, DTO, RELATION_ID, RELATION> mapper,
                                  REPOSITORY repository) {
        super(mapper, repository);
    }

    public DTO save(RELATION_ID relationId, DTO dto) {
        final ENTITY entity = super.mapper.revMap(relationId, dto);
        final ENTITY savedEntity = super.repository.save(entity);
        return super.mapper.map(savedEntity);
    }

    public List<DTO> saveAll(RELATION_ID relationId, Collection<DTO> dtos) {
        final List<ENTITY> entities = dtos.stream()
                .map(dto -> super.mapper.revMap(relationId, dto))
                .collect(toList());
        final List<ENTITY> savedEntities = super.repository.saveAll(entities);
        return super.mapper.map(savedEntities);
    }
}

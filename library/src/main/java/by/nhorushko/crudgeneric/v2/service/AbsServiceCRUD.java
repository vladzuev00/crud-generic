package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
public abstract class AbsServiceCRUD<
        ENTITY_ID,
        ENTITY extends AbstractEntity<ENTITY_ID>,
        DTO extends AbstractDto<ENTITY_ID>,
        REPOSITORY extends JpaRepository<ENTITY, ENTITY_ID>>

        extends AbsServiceRUD<ENTITY_ID, ENTITY, DTO, AbsMapperEntityDto<ENTITY, DTO>, REPOSITORY> {

    public AbsServiceCRUD(AbsMapperEntityDto<ENTITY, DTO> mapper, REPOSITORY repository) {
        super(mapper, repository);
    }

    public DTO save(DTO dto) {
        final ENTITY entity = super.mapper.revMap(dto);
        final ENTITY savedEntity = super.repository.save(entity);
        return super.mapper.map(savedEntity);
    }

    public List<DTO> saveAll(Collection<DTO> dto) {
        final List<ENTITY> entities = super.mapper.revMap(dto);
        final List<ENTITY> savedEntities = super.repository.saveAll(entities);
        return super.mapper.map(savedEntities);
    }
}

package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbstractMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public abstract class AbstractCrudService<
        ENTITY_ID,
        ENTITY extends AbstractEntity<ENTITY_ID>,
        DTO extends AbstractDto<ENTITY_ID>>

        extends AbstractRUDService<ENTITY_ID, ENTITY, DTO, AbstractMapper<ENTITY, DTO>> {

    public AbstractCrudService(AbstractMapper<ENTITY, DTO> mapper, JpaRepository<ENTITY, ENTITY_ID> repository) {
        super(mapper, repository);
    }

    public DTO save(DTO dto) {
        final ENTITY entity = super.mapper.toEntity(dto);
        final ENTITY savedEntity = super.repository.save(entity);
        return super.mapper.toDto(savedEntity);
    }

    public List<DTO> saveAll(Collection<DTO> dto) {
        final List<ENTITY> entities = super.mapper.toEntity(dto);
        final List<ENTITY> savedEntities = super.repository.saveAll(entities);
        return super.mapper.toDto(savedEntities);
    }
}

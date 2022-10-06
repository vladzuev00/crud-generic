package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class CrudService<DTO extends AbstractDto, ENTITY extends AbstractEntity, ENTITY_ID> {
    private final JpaRepository<ENTITY, ENTITY_ID> repository;
    private final Mapper<ENTITY, DTO> mapper;

    public CrudService(JpaRepository<ENTITY, ENTITY_ID> repository, Mapper<ENTITY, DTO> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Optional<DTO> findById(ENTITY_ID id) {
        final Optional<ENTITY> optionalEntity = this.repository.findById(id);
        return optionalEntity.map(this.mapper::toDto);
    }

    public void update(DTO dto) {
        final ENTITY entity = this.mapper.toEntity(dto);
        this.repository.save(entity);
    }

    public void delete(DTO dto) {
        final ENTITY entity = this.mapper.toEntity(dto);
        this.repository.delete(entity);
    }

    public void save(DTO dto) {
        final ENTITY entity = this.mapper.toEntity(dto);
        this.repository.save(entity);
    }

    public void saveAll(Collection<DTO> dto) {
        final List<ENTITY> entities = this.mapper.toEntity(dto);
        this.repository.saveAll(entities);
    }
}

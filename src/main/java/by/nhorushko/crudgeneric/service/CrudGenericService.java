package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class CrudGenericService<
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity,
        REPOSITORY extends CrudRepository<ENTITY, Long>,
        MAPPER extends AbstractMapper<ENTITY, DTO>> {

    protected final REPOSITORY repository;
    protected final MAPPER mapper;
    protected final Class<DTO> dtoClass;
    protected final Class<ENTITY> entityClass;

    public CrudGenericService(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    public List<DTO> list() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(e -> mapper.toDto(e))
                .collect(Collectors.toList());
    }

    public List<DTO> list(Collection<Long> ids) {
        return StreamSupport
                .stream(repository.findAllById(ids).spliterator(), false)
                .map(e -> mapper.toDto(e))
                .collect(Collectors.toList());
    }

    public DTO getById(Long id) {
        ENTITY e = repository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(String.format("Entity %s id: %s was not found", entityClass, id)));
        return mapper.toDto(e);
    }

    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAll(List<DTO> dtos) {
        repository.deleteAll(mapper.toEntity(dtos));
    }

    public DTO save(DTO dto) {
        ENTITY entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public List<DTO> saveAll(List<DTO> list) {
        List<ENTITY> entities = list.stream().map(d -> mapper.toEntity(d)).collect(Collectors.toList());
        return StreamSupport.stream(repository.saveAll(entities).spliterator(), false)
                .map(e -> mapper.toDto(e))
                .collect(Collectors.toList());
    }

    protected boolean isNew(DTO dto) {
        return dto.getId() == null || dto.getId().equals(0);
    }

    protected boolean isNew(ENTITY dto) {
        return dto.getId() == null || dto.getId().equals(0);
    }
}

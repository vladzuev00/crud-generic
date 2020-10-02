package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class ImmutableGenericService <
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity,
        REPOSITORY extends CrudRepository<ENTITY, Long>,
        MAPPER extends AbstractMapper<ENTITY, DTO>> {

    protected final REPOSITORY repository;
    protected final MAPPER mapper;
    protected final Class<DTO> dtoClass;
    protected final Class<ENTITY> entityClass;

    public ImmutableGenericService(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass) {
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
        return mapper.toDto(findById(id));
    }

    private ENTITY findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(String.format("Entity %s id: %s was not found", entityClass, id)));
    }

    public List<DTO> getById(Collection<Long> ids) {
        return StreamSupport
                .stream(repository.findAllById(ids).spliterator(), false)
                .map(e -> mapper.toDto(e))
                .collect(Collectors.toList());
    }

    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }
}

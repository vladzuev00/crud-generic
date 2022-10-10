package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public abstract class AbstractReadService<
        ID,
        ENTITY extends AbstractEntity<ID>,
        DTO extends AbstractDto<ID>,
        MAPPER extends Mapper<ENTITY, DTO>> {

    protected final MAPPER mapper;
    protected final JpaRepository<ENTITY, ID> repository;

    public AbstractReadService(MAPPER mapper, JpaRepository<ENTITY, ID> repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Optional<DTO> findById(ID id) {
        return this.repository.findById(id)
                .map(this.mapper::toDto);
    }

    public List<DTO> findById(Collection<ID> ids) {
        final List<ENTITY> entities = this.repository.findAllById(ids);
        return this.mapper.toDto(entities);
    }

    public DTO getById(ID id) {
        return this.findById(id)
                .orElseThrow(() -> new AppNotFoundException(format("No entity with id = '%s'.", id)));
    }

    public boolean isExist(ID id) {
        return this.repository.existsById(id);
    }
}

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
        MAPPER extends Mapper<ENTITY, DTO>,
        REPOSITORY extends JpaRepository<ENTITY, ID>> {

    protected final MAPPER mapper;
    protected final REPOSITORY repository;

    public AbstractReadService(MAPPER mapper, REPOSITORY repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Optional<DTO> getByIdOptional(ID id) {
        return this.repository.findById(id)
                .map(this.mapper::toDto);
    }

    public List<DTO> getById(Collection<ID> ids) {
        final List<ENTITY> entities = this.repository.findAllById(ids);
        return this.mapper.toDto(entities);
    }

    public DTO getById(ID id) {
        return this.getByIdOptional(id)
                .orElseThrow(() -> new AppNotFoundException(format("Entity id: %s was not found", id)));
    }

    public boolean isExist(ID id) {
        return this.repository.existsById(id);
    }
}

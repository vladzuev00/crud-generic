package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Only read service
 */
@Transactional
public abstract class AbsServiceR<
        ID,
        ENTITY extends AbstractEntity<ID>,
        DTO extends AbstractDto<ID>,
        MAPPER extends AbsMapperDto<ENTITY, DTO>,
        REPOSITORY extends JpaRepository<ENTITY, ID>> {

    protected final MAPPER mapper;
    protected final REPOSITORY repository;

    public AbsServiceR(MAPPER mapper, REPOSITORY repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Optional<DTO> getByIdOptional(ID id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    public List<DTO> getById(Collection<ID> ids) {
        List<ENTITY> entities = repository.findAllById(ids);
        return mapper.toDtos(entities);
    }

    public DTO getById(ID id) {
        return getByIdOptional(id)
                .orElseThrow(() -> new AppNotFoundException(format("Entity id: %s was not found", id)));
    }

    public boolean isExist(ID id) {
        return repository.existsById(id);
    }
}

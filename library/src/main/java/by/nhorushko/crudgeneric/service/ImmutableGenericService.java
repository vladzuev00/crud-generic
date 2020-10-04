package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import by.nhorushko.crudgeneric.mapper.DtoMappers;
import by.nhorushko.crudgeneric.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
public abstract class ImmutableGenericService<
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity,
        REPOSITORY extends JpaRepository<ENTITY, Long>,
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
        return mapper.toDto(repository.findAll());
    }

    public <DTO_PARTIAL extends AbstractDto> List<DTO_PARTIAL> list(Class<DTO_PARTIAL> dtoClass) {
        Mapper mapper = DtoMappers.get(entityClass, dtoClass);
        return mapper.toDto(repository.findAll());
    }

    public List<DTO> list(Collection<Long> ids) {
        return mapper.toDto(repository.findAllById(ids));
    }

    public <DTO_PARTIAL extends AbstractDto> List<DTO_PARTIAL>  list(Collection<Long> ids, Class<DTO_PARTIAL> dto_partialClass) {
        Mapper mapper = DtoMappers.get(entityClass, dto_partialClass);
        return mapper.toDto(repository.findAllById(ids));
    }

    public DTO getById(Long id) {
        return mapper.toDto(findById(id));
    }

    public <DTO_PARTIAL extends AbstractDto> DTO_PARTIAL getById(Long id, Class<DTO_PARTIAL> dto_partialClass) {
        Mapper<ENTITY, DTO_PARTIAL> mapper = DtoMappers.get(entityClass, dto_partialClass);
        return mapper.toDto(findById(id));
    }

    private ENTITY findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(String.format("Entity %s id: %s was not found", entityClass, id)));
    }

    public List<DTO> getById(Collection<Long> ids) {
        return mapper.toDto(repository.findAllById(ids));
    }

    public<DTO_PARTIAL extends AbstractDto> List<DTO_PARTIAL> getById(Collection<Long> ids, Class<DTO_PARTIAL> dto_partialClass) {
        Mapper mapper = DtoMappers.get(entityClass, dto_partialClass);
        return mapper.toDto(repository.findAllById(ids));
    }

    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }
}

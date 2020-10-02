package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import by.nhorushko.crudgeneric.util.FieldCopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class CrudGenericService<
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity,
        REPOSITORY extends PagingAndSortingRepository<ENTITY, Long> & JpaSpecificationExecutor<ENTITY>,
        MAPPER extends AbstractMapper<ENTITY, DTO>> extends PagingAndSortingImmutableGenericService<DTO, ENTITY, REPOSITORY, MAPPER> {

    protected Set<String> ignorePartialUpdateProperties = Set.of("id");

    public CrudGenericService(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass) {
        super(repository, mapper, dtoClass, entityClass);
    }

    public CrudGenericService(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass,
                              Set<String> ignorePartialUpdateProperties) {
        super(repository, mapper, dtoClass, entityClass);
        this.ignorePartialUpdateProperties = ignorePartialUpdateProperties;
    }

    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppNotFoundException(String.format("Entity %s with id: %s was not found", entityClass.getSimpleName(), id), ex);
        }
    }

    public void deleteAll(List<DTO> dtos) {
        repository.deleteAll(mapper.toEntity(dtos));
    }

    public DTO save(DTO dto) {
        ENTITY entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public DTO update(DTO obj) {
        return save(obj);
    }

    public DTO updatePartial(Long id, Object source) {
        DTO target = getById(id);
        FieldCopyUtil.copy(source, target, ignorePartialUpdateProperties);
        return save(target);
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

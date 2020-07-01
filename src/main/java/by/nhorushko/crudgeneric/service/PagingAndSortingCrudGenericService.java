package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class PagingAndSortingCrudGenericService <
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity,
        REPOSITORY extends PagingAndSortingRepository<ENTITY, Long> & JpaSpecificationExecutor<ENTITY>,
        MAPPER extends AbstractMapper<ENTITY, DTO>>
        extends CrudGenericService<DTO, ENTITY, REPOSITORY, MAPPER> {

    public PagingAndSortingCrudGenericService(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass) {
        super(repository, mapper, dtoClass, entityClass);
    }

    public List<DTO> list(Pageable pageable, Specification<ENTITY> specs) {
        return StreamSupport
                .stream(repository.findAll(specs, pageable).spliterator(), false)
                .map(e -> mapper.toDto(e))
                .collect(Collectors.toList());
    }
}

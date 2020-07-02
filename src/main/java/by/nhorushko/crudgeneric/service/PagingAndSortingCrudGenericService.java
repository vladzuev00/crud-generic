package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class PagingAndSortingCrudGenericService <
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity,
        REPOSITORY extends PagingAndSortingRepository<ENTITY, Long> & JpaSpecificationExecutor<ENTITY>,
        MAPPER extends AbstractMapper<ENTITY, DTO>>
        extends CrudGenericService<DTO, ENTITY, REPOSITORY, MAPPER> {

    public PagingAndSortingCrudGenericService(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass) {
        super(repository, mapper, dtoClass, entityClass);
    }

    public Page<DTO> list(Pageable pageable, Specification<ENTITY> specs) {
        return repository.findAll(specs, pageable)
                .map(e -> mapper.toDto(e));
    }
}

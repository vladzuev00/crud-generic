package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import by.nhorushko.crudgeneric.mapper.DtoMappers;
import by.nhorushko.crudgeneric.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class PagingAndSortingImmutableGenericService<
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity,
        REPOSITORY extends JpaRepository<ENTITY, Long> & JpaSpecificationExecutor<ENTITY>,
        MAPPER extends AbstractMapper<ENTITY, DTO>>
        extends ImmutableGenericService<DTO, ENTITY, REPOSITORY, MAPPER> {

    public PagingAndSortingImmutableGenericService(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass) {
        super(repository, mapper, dtoClass, entityClass);
    }

    public Page<DTO> list(Pageable pageable, Specification<ENTITY> specs) {
        return repository.findAll(specs, pageable)
                .map(e -> mapper.toDto(e));
    }

    public <DTO_PARTIAL extends AbstractDto> Page<DTO_PARTIAL> list(Pageable pageable, Specification<ENTITY> specs,
                                                                    Class<DTO_PARTIAL> dto_partialClass) {
        final Mapper mapper = DtoMappers.get(entityClass, dto_partialClass);
        return repository.findAll(specs, pageable)
                .map(e -> dto_partialClass.cast(mapper.toDto(e)));
    }
}

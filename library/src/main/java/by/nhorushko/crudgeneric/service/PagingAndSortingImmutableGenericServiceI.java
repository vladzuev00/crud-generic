package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PagingAndSortingImmutableGenericServiceI<DTO extends AbstractDto, ENTITY extends AbstractEntity> {
    Page<DTO> list(Pageable pageable, Specification<ENTITY> specs);
}

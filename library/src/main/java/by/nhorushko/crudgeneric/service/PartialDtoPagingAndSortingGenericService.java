package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings("unchecked")
public class PartialDtoPagingAndSortingGenericService
        <PARTIAL_DTO extends AbstractDto, ENTITY extends AbstractEntity, SERVICE extends PagingAndSortingImmutableGenericService>
        implements PagingAndSortingImmutableGenericServiceI<PARTIAL_DTO, ENTITY> {

    protected final SERVICE service;
    protected final Class partialDtoClass;

    public PartialDtoPagingAndSortingGenericService(SERVICE service, Class partialDtoClass) {
        this.service = service;
        this.partialDtoClass = partialDtoClass;
    }

    @Override
    public Page<PARTIAL_DTO> list(Pageable pageable, Specification<ENTITY> specs) {
        return service.list(pageable, specs, partialDtoClass);
    }
}

package by.nhorushko.crudgeneric.v2.pageable;

import by.nhorushko.crudgeneric.util.PageableUtils;
import by.nhorushko.crudgeneric.util.SpecificationUtils;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public abstract class AbsPagingAndSortingService<ID,
        DTO extends AbstractDto<ID>,
        ENTITY extends AbstractEntity<ID>,
        SPECS extends AbsFilterSpecification<ENTITY>> {

    protected final JpaSpecificationExecutor<ENTITY> repository;
    protected final AbsMapperDto<ENTITY, DTO> mapper;
    protected final SPECS filterSpecs;

    public AbsPagingAndSortingService(JpaSpecificationExecutor<ENTITY> repository,
                                      AbsMapperDto<ENTITY, DTO> mapper,
                                      SPECS filterSpecs) {
        this.repository = repository;
        this.mapper = mapper;
        this.filterSpecs = filterSpecs;
    }

    protected Specification<ENTITY> buildSpecs(PageFilterRequest pageFilterRequest) {
        var result = new Specification[pageFilterRequest.getFilters().size()];
        int i = 0;
        for (PageFilterRequest.Filter filter : pageFilterRequest.getFilters()) {
            result[i++] = buildSpecification(filter)
                    .orElseThrow(() -> new RuntimeException(String.format("Specification for filter: %s can't be build. Set entity path for: '%s", filter, filter.getName())));
        }
        return concat(result, pageFilterRequest.getConcatCondition());
    }

    protected abstract Optional<Specification<ENTITY>> buildSpecification(PageFilterRequest.Filter filter);

    private Specification<ENTITY> concat(Specification<ENTITY>[] specifications, PageFilterRequest.ConcatCondition condition) {
        if (condition == PageFilterRequest.ConcatCondition.AND) {
            return SpecificationUtils.buildAndSpecs(specifications);
        } else {
            return SpecificationUtils.buildOrSpecs(specifications);
        }
    }

    public Page<DTO> page(PageFilterRequest request) {
        Pageable pageable = PageableUtils.buildPageRequest(request.getPage(), request.getPageSize(), filterSpecs.handleSort(request));
        if (request.getFilters().isEmpty()) {
            return repository.findAll(null, pageable)
                    .map(mapper::toDto);
        } else {
            return repository.findAll(buildSpecs(request), pageable)
                    .map(mapper::toDto);
        }
    }
}


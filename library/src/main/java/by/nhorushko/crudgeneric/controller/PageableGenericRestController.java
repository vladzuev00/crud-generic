package by.nhorushko.crudgeneric.controller;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.domain.SettingsVoid;
import by.nhorushko.crudgeneric.service.PagingAndSortingImmutableGenericService;
import by.nhorushko.crudgeneric.util.PageableUtils;
import by.nhorushko.crudgeneric.util.SpecificationUtils;
import by.nhorushko.filterspecification.FilterOperation;
import by.nhorushko.filterspecification.FilterSpecificationAbstract;
import by.nhorushko.filterspecification.FilterSpecificationConstants;
import by.nhorushko.filterspecification.FilterSpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.Set;

@Deprecated
public abstract class PageableGenericRestController
        <DTO_INTERMEDIATE extends AbstractDto,
                DTO_VIEW extends AbstractDto,
                ENTITY extends AbstractEntity,
                SETTINGS extends SettingsVoid,
                CRUD_SERVICE extends PagingAndSortingImmutableGenericService<DTO_INTERMEDIATE, ENTITY, ?, ?>>
        extends ImmutableGenericRestController<DTO_INTERMEDIATE, DTO_VIEW, SETTINGS, CRUD_SERVICE> {

    protected final static String PARAM_NAME_PAGE = "page";
    protected final static String PARAM_NAME_SIZE = "size";
    protected final static String PARAM_NAME_SORT = "sort";
    protected final static String FILTER_SUFFIX = FilterSpecificationConstants.FILTER_SUFFIX;

    protected final FilterSpecificationAbstract<ENTITY> filterSpecs;

    public PageableGenericRestController(CRUD_SERVICE service, FilterSpecificationAbstract<ENTITY> filterSpecs) {
        super(service);
        this.filterSpecs = filterSpecs;
    }

    protected boolean filterParamIsBlank(String filterParam) {
        return FilterSpecificationUtils.isBlank(filterParam);
    }

    protected void checkFilterOperation(String filter, Set<FilterOperation> availableOperations) {
        FilterSpecificationUtils.checkFilterOperation(filter, availableOperations);
    }

    @SafeVarargs
    protected final Page<DTO_INTERMEDIATE> getPage(int page, int size, String sort, Specification<ENTITY>... specs) {
        if (filterSpecs == null) {
            throw new IllegalArgumentException("Not specify filter specification");
        }
        Specification<ENTITY> rSpecs = buildAndSpecs(specs);
        Pageable pageable = buildPageRequest(page, size, sort, filterSpecs.getEntityFieldPaths());
        return service.list(pageable, rSpecs);
    }

    @SafeVarargs
    protected final Page<DTO_INTERMEDIATE> getPageOr(int page, int size, String sort, Specification<ENTITY>... specs) {
        if (filterSpecs == null) {
            throw new IllegalArgumentException("Not specify filter specification");
        }
        Specification<ENTITY> rSpecs = buildOrSpecs(specs);
        Pageable pageable = buildPageRequest(page, size, sort, filterSpecs.getEntityFieldPaths());
        return service.list(pageable, rSpecs);
    }

    protected Page<DTO_VIEW> postHandle(Page<DTO_INTERMEDIATE> dto, SETTINGS settings) {
        return dto.map(d -> postHandle(d, settings));
    }

    @SafeVarargs
    protected final Specification<ENTITY> buildAndSpecs(Specification<ENTITY>... specs) {
        return SpecificationUtils.buildAndSpecs(specs);
    }

    @SafeVarargs
    protected final Specification<ENTITY> buildOrSpecs(Specification<ENTITY>... specs) {
        return SpecificationUtils.buildOrSpecs(specs);
    }

    protected Pageable buildPageRequest(int page, int size, String sort, Map<String, String> entityFieldPaths) {
        sort = sort.replace("asc#", "+");
        sort = sort.replace("desc#", "-");
        return PageableUtils.buildPageRequest(page, size, sort, entityFieldPaths);
    }
}

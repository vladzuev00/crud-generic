package by.nhorushko.crudgeneric.v2.pageable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PageFilterRequest {

    private int page;
    private int pageSize;
    /**
     * sort look like sign + OR - and property name
     * +id
     * -id
     * +name
     * -name
     */
    private String sort;
    private Filters filters;

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getSort() {
        return sort;
    }

    public List<Filter> getFilters() {
        return filters.filters;
    }

    public ConcatCondition getConcatCondition() {
        return filters.condition;
    }

    private PageFilterRequest(int page, int pageSize, String sort, ConcatCondition condition, Filter... filters) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
        this.filters = new Filters(
                Arrays.stream(filters)
                        .filter(f -> StringUtils.isNotEmpty(f.getFilter()))
                        .collect(Collectors.toList()),
                condition);
    }

    public static PageFilterRequest pageRequestOr(int page, int pageSize, String sort, Filter... filters) {
        return new PageFilterRequest(page, pageSize, sort, ConcatCondition.OR, filters);
    }

    public static PageFilterRequest pageRequestAnd(int page, int pageSize, String sort, Filter... filters) {
        return new PageFilterRequest(page, pageSize, sort, ConcatCondition.AND, filters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageFilterRequest that = (PageFilterRequest) o;
        return page == that.page && pageSize == that.pageSize && sort.equals(that.sort) && filters.equals(that.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pageSize, sort, filters);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", sort='" + sort + '\'' +
                ", filters=" + filters +
                '}';
    }

    public static class Filters {
        private final List<Filter> filters;
        private final ConcatCondition condition;

        public Filters(List<Filter> filters, ConcatCondition condition) {
            this.filters = filters;
            this.condition = condition;
        }

        public boolean isEmpty() {
            return CollectionUtils.isEmpty(filters);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Filters filters1 = (Filters) o;
            return Objects.equals(filters, filters1.filters) && condition == filters1.condition;
        }

        @Override
        public int hashCode() {
            return Objects.hash(filters, condition);
        }

        @Override
        public String toString() {
            return "Filters{" +
                    "filters=" + filters +
                    ", condition=" + condition +
                    '}';
        }
    }

    public static class Filter {
        private final String name;
        private final String filter;

        public Filter(String name, String filter) {
            this.name = name;
            this.filter = filter;
        }

        public String getName() {
            return name;
        }

        public String getFilter() {
            return filter;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Filter filter1 = (Filter) o;
            return Objects.equals(name, filter1.name) && Objects.equals(filter, filter1.filter);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, filter);
        }

        @Override
        public String toString() {
            return "Filter{" +
                    "name='" + name + '\'' +
                    ", filter='" + filter + '\'' +
                    '}';
        }
    }

    enum ConcatCondition {
        OR, AND
    }

}

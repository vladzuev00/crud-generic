package by.nhorushko.crudgeneric.util;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {

    public static <E> Specification<E> buildAndSpecs(Specification<E>... specs) {
        if (specs.length == 0) {
            throw new IllegalArgumentException("expect at least one Specification");
        }
        Specification<E> result = specs[0];
        for (int i = 1; i < specs.length; i++) {
            result = result.and(specs[i]);
        }
        return Specification.where(result);
    }

    public static <E> Specification<E> buildOrSpecs(Specification<E>... specs) {
        if (specs.length == 0) {
            throw new IllegalArgumentException("expect at least one Specification");
        }
        Specification<E> result = specs[0];
        for (int i = 1; i < specs.length; i++) {
            result = result.or(specs[i]);
        }
        return Specification.where(result);
    }
}

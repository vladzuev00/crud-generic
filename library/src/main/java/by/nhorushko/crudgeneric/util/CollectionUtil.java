package by.nhorushko.crudgeneric.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public final class CollectionUtil {

    public static <S, R> List<R> mapToList(final Collection<S> sources, final Function<S, R> mapper) {
        return sources.stream()
                .map(mapper)
                .collect(toList());
    }

    private CollectionUtil() {
        throw new UnsupportedOperationException();
    }
}

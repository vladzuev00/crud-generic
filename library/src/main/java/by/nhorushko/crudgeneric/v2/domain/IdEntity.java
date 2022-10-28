package by.nhorushko.crudgeneric.v2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IdEntity<ID> {

    ID getId();

    @JsonIgnore
    default boolean isNew() {
        if (getId() == null) {
            return true;
        }
        if (getId() instanceof Number) {
            return ((Number) getId()).longValue() == 0;
        }
        return false;
    }
}

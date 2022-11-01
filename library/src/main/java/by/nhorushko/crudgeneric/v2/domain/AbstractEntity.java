package by.nhorushko.crudgeneric.v2.domain;

/**
 * This interface mark pojo as Entity
 */
public interface AbstractEntity<ID> extends IdEntity<ID> {
    void setId(ID id);
}

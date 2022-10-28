package by.nhorushko.crudgeneric.v2.domain;

public interface AbstractEntity<ID> extends IdEntity<ID> {
    void setId(ID id);
}

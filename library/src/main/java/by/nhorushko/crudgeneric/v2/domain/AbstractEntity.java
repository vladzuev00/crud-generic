package by.nhorushko.crudgeneric.v2.domain;

public interface AbstractEntity<IdType> {
    void setId(IdType id);
    IdType getId();
}

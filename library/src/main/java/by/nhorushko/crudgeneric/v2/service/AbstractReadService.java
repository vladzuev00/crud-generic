package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import by.nhorushko.crudgeneric.v2.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class AbstractReadService<
        ID,
        ENTITY extends AbstractEntity<ID>,
        DTO extends AbstractDto<ID>,
        MAPPER extends Mapper<ENTITY, DTO>> {

    protected final MAPPER mapper;
    protected final JpaRepository<ENTITY, ID> repository;

    public AbstractReadService(MAPPER mapper, JpaRepository<ENTITY, ID> repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Optional<DTO> getById(ID id) {
        return this.repository.findById(id)
                .map(this.mapper::toDto);
    }
}

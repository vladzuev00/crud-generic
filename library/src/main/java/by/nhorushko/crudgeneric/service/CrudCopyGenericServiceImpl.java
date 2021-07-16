package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public class CrudCopyGenericServiceImpl<
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity & Serializable,
        REPOSITORY extends JpaRepository<ENTITY, Long> & JpaSpecificationExecutor<ENTITY>,
        MAPPER extends AbstractMapper<ENTITY, DTO>> extends CrudGenericService<DTO, ENTITY, REPOSITORY, MAPPER> {

    public CrudCopyGenericServiceImpl(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass) {
        super(repository, mapper, dtoClass, entityClass);
    }

    public DTO copy(Long id) {
        ENTITY entity = findEntityById(id);
        ENTITY clone = SerializationUtils.clone(entity);
        clone.setId(null);
        return mapper.toDto(clone);
    }
}

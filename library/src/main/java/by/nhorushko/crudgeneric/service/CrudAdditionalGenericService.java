package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public abstract class CrudAdditionalGenericService<
        DTO extends AbstractDto,
        ENTITY extends AbstractEntity,
        REPOSITORY extends JpaRepository<ENTITY, Long> & JpaSpecificationExecutor<ENTITY>,
        MAPPER extends AbstractMapper<ENTITY, DTO>> extends RudGenericService<DTO, ENTITY, REPOSITORY, MAPPER> {

    public CrudAdditionalGenericService(REPOSITORY repository, MAPPER mapper, Class<DTO> dtoClass, Class<ENTITY> entityClass) {
        super(repository, mapper, dtoClass, entityClass);
    }

    public DTO save(Long rootId, DTO dto) {
        ENTITY entity = mapper.toEntity(dto);
        setupEntityBeforeSave(rootId, entity);
        return mapper.toDto(repository.save(entity));
    }

    public List<DTO> saveAll(Long rootId, Collection<DTO> list) {
        List<ENTITY> entities = list.stream()
                .map(dto -> {
                    ENTITY e = mapper.toEntity(dto);
                    setupEntityBeforeSave(rootId, e);
                    return e;
                })
                .collect(Collectors.toList());
        return repository.saveAll(entities).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    protected abstract void setupEntityBeforeUpdate(ENTITY source, ENTITY target);

    protected abstract void setupEntityBeforeSave(Long rootId, ENTITY entity);
}

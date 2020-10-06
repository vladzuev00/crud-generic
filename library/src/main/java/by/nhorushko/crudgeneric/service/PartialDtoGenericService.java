package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
public class PartialDtoGenericService
        <PARTIAL_DTO extends AbstractDto, ENTITY extends AbstractEntity>
        implements ImmutableGenericServiceI <PARTIAL_DTO> {

    protected final Class<PARTIAL_DTO> partialDtoClass;
    protected final Class<ENTITY> entityClass;
    protected final ImmutableGenericService immutableGenericService;

    public PartialDtoGenericService(Class<PARTIAL_DTO> partialDtoClass,
                                    Class<ENTITY> entityClass,
                                    ImmutableGenericService immutableGenericService) {
        this.partialDtoClass = partialDtoClass;
        this.entityClass = entityClass;
        this.immutableGenericService = immutableGenericService;
    }

    @Override
    public List<PARTIAL_DTO> list() {
        return immutableGenericService.list(partialDtoClass);
    }

    @Override
    public PARTIAL_DTO getById(Long id) {
        return (PARTIAL_DTO) immutableGenericService.getById(id, partialDtoClass);
    }

    @Override
    public List<PARTIAL_DTO> getById(Collection<Long> ids) {
        return immutableGenericService.getById(ids, partialDtoClass);
    }

    @Override
    public boolean existById(Long id) {
        return immutableGenericService.existById(id);
    }

    @Override
    public long count() {
        return immutableGenericService.count();
    }
}

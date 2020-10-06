package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;

import java.util.Collection;
import java.util.List;

public interface ImmutableGenericServiceI<DTO extends AbstractDto> {

    List<DTO> list();

    DTO getById(Long id);

    List<DTO> getById(Collection<Long> ids);

    boolean existById(Long id);

    long count();
}

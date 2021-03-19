package by.nhorushko.crudgeneric.mapper;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;

import java.util.Collection;
import java.util.List;

public interface Mapper<ENTITY extends AbstractEntity, DTO extends AbstractDto> {

    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntity(Collection<? extends DTO> dtos);

    DTO toDto(ENTITY entity);

    List<DTO> toDto(Collection<ENTITY> entities);
}

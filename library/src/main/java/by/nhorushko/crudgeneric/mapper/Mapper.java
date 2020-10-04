package by.nhorushko.crudgeneric.mapper;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;

import java.util.List;

public interface Mapper<ENTITY extends AbstractEntity, DTO extends AbstractDto> {

    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntity(List<DTO> dtos);

    DTO toDto(ENTITY entity);

    List<DTO> toDto(List<ENTITY> entities);
}

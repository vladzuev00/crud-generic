package by.aurorasoft.crudgeneric.mapper;

import java.util.List;

public interface Mapper<ENTITY, DTO> {

    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntity(List<DTO> dtos);

    DTO toDto(ENTITY entity);

    List<DTO> toDto(List<ENTITY> entities);
}

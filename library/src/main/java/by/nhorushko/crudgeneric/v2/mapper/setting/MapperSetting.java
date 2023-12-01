package by.nhorushko.crudgeneric.v2.mapper.setting;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import lombok.Value;
import org.modelmapper.Converter;

@Value
public class MapperSetting<DTO extends AbstractDto<?>, ENTITY extends AbstractEntity<?>> {
    Class<DTO> dtoType;
    Class<ENTITY> entityType;
    Converter<DTO, ENTITY> dtoToEntityPostConvertor;
    Converter<ENTITY, DTO> entityToDtoPostConvertor;
}

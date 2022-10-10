package by.nhorushko.crudgeneric.domain.dto;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Value;

@Value
public class Car implements AbstractDto<Long> {
    Long id;
    String number;
}

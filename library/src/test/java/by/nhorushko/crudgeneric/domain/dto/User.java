package by.nhorushko.crudgeneric.domain.dto;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Value;

@Value
public class User implements AbstractDto<Long> {
    Long id;
    String email;
    String name;
    String surname;
    String patronymic;
    Car car;
}

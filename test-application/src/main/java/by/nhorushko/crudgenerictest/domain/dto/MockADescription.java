package by.nhorushko.crudgenerictest.domain.dto;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MockADescription implements AbstractDto {
    Long id;
    String description;
}

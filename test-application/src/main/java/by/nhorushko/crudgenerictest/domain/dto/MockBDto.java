package by.nhorushko.crudgenerictest.domain.dto;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import lombok.Value;

@Value
public class MockBDto implements AbstractDto {
    Long id;
    String name;
    MockDto mockDto;
}

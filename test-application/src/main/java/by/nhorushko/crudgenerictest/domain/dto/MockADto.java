package by.nhorushko.crudgenerictest.domain.dto;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MockADto implements AbstractDto {

    private Long id;
    private String name;
    private String description;

    public MockADto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

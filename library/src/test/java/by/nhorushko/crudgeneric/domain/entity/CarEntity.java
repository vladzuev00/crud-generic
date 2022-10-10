package by.nhorushko.crudgeneric.domain.entity;

import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class CarEntity implements AbstractEntity<Long> {
    private Long id;
    private String number;
}

package by.nhorushko.crudgeneric.domain.entity;

import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class UserEntity implements AbstractEntity<Long> {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String patronymic;
    private CarEntity car;
}

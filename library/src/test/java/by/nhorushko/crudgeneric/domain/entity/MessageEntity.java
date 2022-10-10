package by.nhorushko.crudgeneric.domain.entity;

import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class MessageEntity implements AbstractEntity<Long> {
    private Long id;
    private float latitude;
    private float longitude;
    private int speed;
    private int course;
    private int altitude;
}

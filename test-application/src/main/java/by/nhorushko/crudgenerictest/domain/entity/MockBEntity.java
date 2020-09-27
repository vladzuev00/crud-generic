package by.nhorushko.crudgenerictest.domain.entity;

import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgenerictest.domain.dto.MockADto;
import by.nhorushko.crudgenerictest.domain.dto.MockBDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.util.Set;

import static by.nhorushko.crudgeneric.util.FieldCopyUtil.copy;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MockBEntity implements AbstractEntity {
    @Id
    private Long id;
    private String name;
    @OneToOne
    private MockAEntity mockAEntity;

    public MockBEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

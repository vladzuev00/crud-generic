package by.nhorushko.crudgenerictest.service;

import by.nhorushko.crudgenerictest.domain.dto.Driver;
import by.nhorushko.crudgenerictest.domain.entity.DriverEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class DriverServiceTest {

    @Autowired
    DriverService service;
    @Autowired
    EntityManager entityManager;

    @Test
    public void save() {
        Driver driver = new Driver(null, "Dominic Torretto");
        service.save(1L, driver);
    }

    @Test
    @Sql(statements = "INSERT INTO driver (id, name, user_id) VALUES (33, 'Иваныч',1)")
    public void update() {
        Driver driver = new Driver(33L, "Dominic Torretto");
        service.update(driver);

        entityManager.flush();
        var actual = entityManager.find(DriverEntity.class, 33L);
        entityManager.refresh(actual);
        assertEquals(1L, actual.getUser().getId());
    }
}
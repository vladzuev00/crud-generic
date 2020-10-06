package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgenerictest.domain.dto.MockADescription;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MockADescriptionServiceTest {

    @Autowired
    private MockADescriptionService service;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void listTest_ShouldReturnList() {
        List<MockADescription> expected = List.of(
                new MockADescription(1L, "description-1"),
                new MockADescription(2L, "description-2"),
                new MockADescription(3L, "description-3"),
                new MockADescription(4L, "description-4"),
                new MockADescription(5L, "description-5"));
        List<MockADescription> actual = service.list();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getById_ShouldReturn() {
        MockADescription expected = new MockADescription(1L, "description-1");
        MockADescription actual = service.getById(1L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdList_ShouldReturnList() {
        List<MockADescription> expected = List.of(
                new MockADescription(1L, "description-1"),
                new MockADescription(2L, "description-2"),
                new MockADescription(3L, "description-3"));
        List<MockADescription> actual = service.getById(List.of(1L, 2L, 3L));
        assertEquals(expected, actual);
    }
}

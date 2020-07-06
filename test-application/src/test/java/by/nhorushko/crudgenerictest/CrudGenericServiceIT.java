package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgenerictest.domain.dto.MockDto;
import by.nhorushko.crudgenerictest.domain.entity.MockEntity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CrudGenericServiceIT {

    @Autowired
    private MockService mockService;

    @Autowired
    private EntityManager entityManager;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void listTest_ShouldReturnList() {
        List<MockDto> expected = List.of(
                new MockDto(1l, "test-1"),
                new MockDto(2l, "test-2"),
                new MockDto(3l, "test-3"),
                new MockDto(4l, "test-4"),
                new MockDto(5l, "test-5"));

        List<MockDto> actual = mockService.list();
        assertEquals(expected, actual);
    }

    @Test
    public void listTest_ShouldReturnZeroList() {
        List<MockDto> expected = List.of();
        List<MockDto> actual = mockService.list();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void listTestShouldReturnSpecified() {
        List<MockDto> expected = List.of(
                new MockDto(2l, "test-2"),
                new MockDto(3l, "test-3"));
        List<MockDto> actual = mockService.list(Set.of(2l, 3l));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest() {
        MockDto expected = new MockDto(2l, "test-2");
        MockDto actual = mockService.getById(2l);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldThrowNotFoundException() {
        exceptionRule.expect(AppNotFoundException.class);
        exceptionRule.expectMessage("9999 was not found");
        mockService.getById(9999l);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList() {
        List<MockDto> expected = List.of(
                new MockDto(2l, "test-2"),
                new MockDto(3l, "test-3"));
        List<MockDto> actual = mockService.getById(Set.of(2l, 3l));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList2() {
        List<MockDto> expected = List.of(
                new MockDto(2l, "test-2"));
        List<MockDto> actual = mockService.getById(Set.of(2l, 9999l));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList3() {
        List<MockDto> expected = List.of();
        List<MockDto> actual = mockService.getById(Set.of(8888l, 9999l));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList4() {
        List<MockDto> expected = List.of();
        List<MockDto> actual = mockService.getById(Set.of());
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void existByIdTest_ShouldReurtnTrue() {
        boolean actual = mockService.existById(2l);
        assertTrue(actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void existByIdTest_ShouldReurtnFalse() {
        boolean actual = mockService.existById(9999l);
        assertFalse(actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void countTest() {
        long actual = mockService.count();
        assertEquals(5, actual);
    }

    @Test
    public void countTest_ShouldBeZero() {
        long actual = mockService.count();
        assertEquals(0, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest_ShouldBeNullAfterDelete() {
        assertNotNull(entityManager.find(MockEntity.class, 5l));
        mockService.deleteById(5l);
        assertNull(entityManager.find(MockEntity.class, 5l));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest_ShouldThrowAppNotFoundException() {
        exceptionRule.expect(AppNotFoundException.class);
        exceptionRule.expectMessage("9999 was not found");
        mockService.deleteById(9999l);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAll() {
        assertNotNull(entityManager.find(MockEntity.class, 1l));
        assertNotNull(entityManager.find(MockEntity.class, 2l));

        List<MockDto> toDel = List.of(
                new MockDto(1l, "test-1"),
                new MockDto(2l, "test-2"));

        mockService.deleteAll(toDel);

        assertNull(entityManager.find(MockEntity.class, 1l));
        assertNull(entityManager.find(MockEntity.class, 2l));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAllShouldDeleteOne() {
        assertNotNull(entityManager.find(MockEntity.class, 1l));
        assertNotNull(entityManager.find(MockEntity.class, 2l));
        assertNotNull(entityManager.find(MockEntity.class, 3l));
        assertNotNull(entityManager.find(MockEntity.class, 4l));
        assertNotNull(entityManager.find(MockEntity.class, 5l));

        List<MockDto> toDel = List.of(
                new MockDto(1l, "test-1"),
                new MockDto(9999l, "test-2"));

        mockService.deleteAll(toDel);

        assertNull(entityManager.find(MockEntity.class, 1l));
        assertNotNull(entityManager.find(MockEntity.class, 2l));
        assertNotNull(entityManager.find(MockEntity.class, 3l));
        assertNotNull(entityManager.find(MockEntity.class, 4l));
        assertNotNull(entityManager.find(MockEntity.class, 5l));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAllShouldDeleteAnyOne() {
        assertNotNull(entityManager.find(MockEntity.class, 1l));
        assertNotNull(entityManager.find(MockEntity.class, 2l));
        assertNotNull(entityManager.find(MockEntity.class, 3l));
        assertNotNull(entityManager.find(MockEntity.class, 4l));
        assertNotNull(entityManager.find(MockEntity.class, 5l));

        List<MockDto> toDel = List.of(
                new MockDto(8888l, "test-1"),
                new MockDto(9999l, "test-2"));

        mockService.deleteAll(toDel);

        assertNotNull(entityManager.find(MockEntity.class, 1l));
        assertNotNull(entityManager.find(MockEntity.class, 2l));
        assertNotNull(entityManager.find(MockEntity.class, 3l));
        assertNotNull(entityManager.find(MockEntity.class, 4l));
        assertNotNull(entityManager.find(MockEntity.class, 5l));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_ShouldSaveNew() {
        assertNull(entityManager.find(MockEntity.class, 6l));
        MockDto actual = mockService.save(new MockDto(55l, "test-6"));
        assertNotNull(entityManager.find(MockEntity.class, actual.getId()));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_ShouldSaveZeroId() {
        MockDto actual = mockService.save(new MockDto(0l, "test-55"));
        assertNotEquals(0l, actual.getId(), 0);
        assertNotNull(entityManager.find(MockEntity.class, actual.getId()));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_UpdateExist() {
        assertNotNull(entityManager.find(MockEntity.class, 1l));
        MockDto given = new MockDto(1l, "updated-test");
        mockService.save(given);
        assertEquals("updated-test", entityManager.find(MockEntity.class, 1l).getName());
    }

}

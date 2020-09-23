package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgenerictest.domain.dto.MockADescription;
import by.nhorushko.crudgenerictest.domain.dto.MockADto;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        List<MockADto> expected = List.of(
                new MockADto(1l, "test-1"),
                new MockADto(2l, "test-2"),
                new MockADto(3l, "test-3"),
                new MockADto(4l, "test-4"),
                new MockADto(5l, "test-5"));

        List<MockADto> actual = mockService.list();
        assertEquals(expected, actual);
    }

    @Test
    public void listTest_ShouldReturnZeroList() {
        List<MockADto> expected = List.of();
        List<MockADto> actual = mockService.list();
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void listTestShouldReturnSpecified() {
        List<MockADto> expected = List.of(
                new MockADto(2l, "test-2"),
                new MockADto(3l, "test-3"));
        List<MockADto> actual = mockService.list(Set.of(2l, 3l));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest() {
        MockADto expected = new MockADto(2l, "test-2");
        MockADto actual = mockService.getById(2l);
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
        List<MockADto> expected = List.of(
                new MockADto(2l, "test-2"),
                new MockADto(3l, "test-3"));
        List<MockADto> actual = mockService.getById(Set.of(2l, 3l));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList2() {
        List<MockADto> expected = List.of(
                new MockADto(2l, "test-2"));
        List<MockADto> actual = mockService.getById(Set.of(2l, 9999l));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList3() {
        List<MockADto> expected = List.of();
        List<MockADto> actual = mockService.getById(Set.of(8888l, 9999l));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList4() {
        List<MockADto> expected = List.of();
        List<MockADto> actual = mockService.getById(Set.of());
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
        assertNotNull(entityManager.find(MockAEntity.class, 5l));
        mockService.deleteById(5l);
        assertNull(entityManager.find(MockAEntity.class, 5l));
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
        assertNotNull(entityManager.find(MockAEntity.class, 1l));
        assertNotNull(entityManager.find(MockAEntity.class, 2l));

        List<MockADto> toDel = List.of(
                new MockADto(1l, "test-1"),
                new MockADto(2l, "test-2"));

        mockService.deleteAll(toDel);

        assertNull(entityManager.find(MockAEntity.class, 1l));
        assertNull(entityManager.find(MockAEntity.class, 2l));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAllShouldDeleteOne() {
        assertNotNull(entityManager.find(MockAEntity.class, 1l));
        assertNotNull(entityManager.find(MockAEntity.class, 2l));
        assertNotNull(entityManager.find(MockAEntity.class, 3l));
        assertNotNull(entityManager.find(MockAEntity.class, 4l));
        assertNotNull(entityManager.find(MockAEntity.class, 5l));

        List<MockADto> toDel = List.of(
                new MockADto(1l, "test-1"),
                new MockADto(9999l, "test-2"));

        mockService.deleteAll(toDel);

        assertNull(entityManager.find(MockAEntity.class, 1l));
        assertNotNull(entityManager.find(MockAEntity.class, 2l));
        assertNotNull(entityManager.find(MockAEntity.class, 3l));
        assertNotNull(entityManager.find(MockAEntity.class, 4l));
        assertNotNull(entityManager.find(MockAEntity.class, 5l));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAllShouldDeleteAnyOne() {
        assertNotNull(entityManager.find(MockAEntity.class, 1l));
        assertNotNull(entityManager.find(MockAEntity.class, 2l));
        assertNotNull(entityManager.find(MockAEntity.class, 3l));
        assertNotNull(entityManager.find(MockAEntity.class, 4l));
        assertNotNull(entityManager.find(MockAEntity.class, 5l));

        List<MockADto> toDel = List.of(
                new MockADto(8888l, "test-1"),
                new MockADto(9999l, "test-2"));

        mockService.deleteAll(toDel);

        assertNotNull(entityManager.find(MockAEntity.class, 1l));
        assertNotNull(entityManager.find(MockAEntity.class, 2l));
        assertNotNull(entityManager.find(MockAEntity.class, 3l));
        assertNotNull(entityManager.find(MockAEntity.class, 4l));
        assertNotNull(entityManager.find(MockAEntity.class, 5l));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_ShouldSaveNew() {
        assertNull(entityManager.find(MockAEntity.class, 6l));
        MockADto actual = mockService.save(new MockADto(55l, "test-6"));
        assertNotNull(entityManager.find(MockAEntity.class, actual.getId()));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_ShouldSaveZeroId() {
        MockADto actual = mockService.save(new MockADto(0l, "test-55"));
        assertNotEquals(0l, actual.getId(), 0);
        assertNotNull(entityManager.find(MockAEntity.class, actual.getId()));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_UpdateExist() {
        MockADescription obj = new MockADescription(1l, "upd");
        mockService.updatePartial(obj);
        assertEquals("test-1", entityManager.find(MockAEntity.class, 1l).getName());
        assertEquals("upd", entityManager.find(MockAEntity.class, 1l).getDescription());
    }
}

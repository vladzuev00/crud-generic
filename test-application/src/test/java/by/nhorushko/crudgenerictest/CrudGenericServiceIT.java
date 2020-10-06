package by.nhorushko.crudgenerictest;

import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgenerictest.domain.dto.MockADescription;
import by.nhorushko.crudgenerictest.domain.dto.MockADto;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
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
        List<MockADto> expected = List.of(
                new MockADto(1L, "test-1", "description-1"),
                new MockADto(2L, "test-2", "description-2"),
                new MockADto(3L, "test-3", "description-3"),
                new MockADto(4L, "test-4", "description-4"),
                new MockADto(5L, "test-5", "description-5"));

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
    public void listTest_MockADescription() {
        List<MockADescription> expected = List.of(
                new MockADescription(1L, "description-1"),
                new MockADescription(2L, "description-2"),
                new MockADescription(3L, "description-3"),
                new MockADescription(4L, "description-4"),
                new MockADescription(5L, "description-5"));

        List<MockADescription> actual = mockService.list(MockADescription.class);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest() {
        MockADto expected = new MockADto(2L, "test-2", "description-2");
        MockADto actual = mockService.getById(2L);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_DtoPartial() {
        MockADescription expected = new MockADescription(2L, "description-2");
        MockADescription actual = mockService.getById(2L, MockADescription.class);
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldThrowNotFoundException() {
        exceptionRule.expect(AppNotFoundException.class);
        exceptionRule.expectMessage("9999 was not found");
        mockService.getById(9999L);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList() {
        List<MockADto> expected = List.of(
                new MockADto(2L, "test-2", "description-2"),
                new MockADto(3L, "test-3", "description-3"));
        List<MockADto> actual = mockService.getById(Set.of(2L, 3L));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList2() {
        List<MockADto> expected = List.of(
                new MockADto(2L, "test-2", "description-2"));
        List<MockADto> actual = mockService.getById(Set.of(2L, 9999L));
        assertEquals(expected, actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getByIdTest_ShouldReturnList3() {
        List<MockADto> expected = List.of();
        List<MockADto> actual = mockService.getById(Set.of(8888L, 9999L));
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
        boolean actual = mockService.existById(2L);
        assertTrue(actual);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void existByIdTest_ShouldReurtnFalse() {
        boolean actual = mockService.existById(9999L);
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
        assertNotNull(entityManager.find(MockAEntity.class, 5L));
        mockService.deleteById(5L);
        assertNull(entityManager.find(MockAEntity.class, 5L));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteByIdTest_ShouldThrowAppNotFoundException() {
        exceptionRule.expect(AppNotFoundException.class);
        exceptionRule.expectMessage("9999 was not found");
        mockService.deleteById(9999L);
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAll() {
        assertNotNull(entityManager.find(MockAEntity.class, 1L));
        assertNotNull(entityManager.find(MockAEntity.class, 2L));

        List<MockADto> toDel = List.of(
                new MockADto(1L, "test-1"),
                new MockADto(2L, "test-2"));

        mockService.deleteAll(toDel);

        assertNull(entityManager.find(MockAEntity.class, 1L));
        assertNull(entityManager.find(MockAEntity.class, 2L));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAllShouldDeleteOne() {
        assertNotNull(entityManager.find(MockAEntity.class, 1L));
        assertNotNull(entityManager.find(MockAEntity.class, 2L));
        assertNotNull(entityManager.find(MockAEntity.class, 3L));
        assertNotNull(entityManager.find(MockAEntity.class, 4L));
        assertNotNull(entityManager.find(MockAEntity.class, 5L));

        List<MockADto> toDel = List.of(
                new MockADto(1L, "test-1"),
                new MockADto(9999L, "test-2"));

        mockService.deleteAll(toDel);

        assertNull(entityManager.find(MockAEntity.class, 1L));
        assertNotNull(entityManager.find(MockAEntity.class, 2L));
        assertNotNull(entityManager.find(MockAEntity.class, 3L));
        assertNotNull(entityManager.find(MockAEntity.class, 4L));
        assertNotNull(entityManager.find(MockAEntity.class, 5L));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAllShouldDeleteAnyOne() {
        assertNotNull(entityManager.find(MockAEntity.class, 1L));
        assertNotNull(entityManager.find(MockAEntity.class, 2L));
        assertNotNull(entityManager.find(MockAEntity.class, 3L));
        assertNotNull(entityManager.find(MockAEntity.class, 4L));
        assertNotNull(entityManager.find(MockAEntity.class, 5L));

        List<MockADto> toDel = List.of(
                new MockADto(8888L, "test-1"),
                new MockADto(9999L, "test-2"));

        mockService.deleteAll(toDel);

        assertNotNull(entityManager.find(MockAEntity.class, 1L));
        assertNotNull(entityManager.find(MockAEntity.class, 2L));
        assertNotNull(entityManager.find(MockAEntity.class, 3L));
        assertNotNull(entityManager.find(MockAEntity.class, 4L));
        assertNotNull(entityManager.find(MockAEntity.class, 5L));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_ShouldSaveNew() {
        assertNull(entityManager.find(MockAEntity.class, 6L));
        MockADto actual = mockService.save(new MockADto(55L, "test-6"));
        assertNotNull(entityManager.find(MockAEntity.class, actual.getId()));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_ShouldSaveZeroId() {
        MockADto actual = mockService.save(new MockADto(0L, "test-55"));
        assertNotEquals(0L, actual.getId(), 0);
        assertNotNull(entityManager.find(MockAEntity.class, actual.getId()));
    }

    @Test
    @Sql(value = {"classpath:add-entities-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"classpath:add-entities-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void saveTest_UpdateExist() {
        MockADescription obj = new MockADescription(1L, "upd");
        mockService.updatePartial(1L, obj);
        assertEquals("test-1", entityManager.find(MockAEntity.class, 1L).getName());
        assertEquals("upd", entityManager.find(MockAEntity.class, 1L).getDescription());
    }
}

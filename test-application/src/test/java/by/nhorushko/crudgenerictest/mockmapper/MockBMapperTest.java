package by.nhorushko.crudgenerictest.mockmapper;

import by.nhorushko.crudgenerictest.domain.dto.MockBDto;
import by.nhorushko.crudgenerictest.domain.dto.MockDto;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import by.nhorushko.crudgenerictest.domain.entity.MockBEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MockBMapperTest {

    @Autowired
    MockBMapper mockBMapper;

    @Test
    void test1() {
        MockBDto mockBDto = new MockBDto(1l, "name", new MockDto(1l, "ssss"));
        MockBEntity actual = mockBMapper.toEntity(mockBDto);

        MockBEntity expected = new MockBEntity(1l, "name", new MockAEntity(1l, "ssss"));
        assertEquals(expected, actual);
    }

    @Test
    void toDto_ObjNull() {
        MockBDto mockBDto = new MockBDto(1l, "name", null);
        MockBEntity actual = mockBMapper.toEntity(mockBDto);

        MockBEntity expected = new MockBEntity(1l, "name", null);
        assertEquals(expected, actual);
    }
}

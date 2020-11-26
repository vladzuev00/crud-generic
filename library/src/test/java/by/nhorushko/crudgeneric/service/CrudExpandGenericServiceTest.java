package by.nhorushko.crudgeneric.service;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.mapper.AbstractMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;

public class CrudExpandGenericServiceTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    @Mock
    private JpaRepository repository;
    @Mock
    private AbstractMapper mapper;

    private CrudExpandGenericService service = new CrudExpandGenericService(repository, mapper, TestAbstract.class, AbstractEntity.class) {
        @Override
        protected AbstractDto createEmptyDto(Long id) {
            return null;
        }

        @Override
        protected void setupEntityBeforeUpdate(AbstractEntity source, AbstractEntity target) {
        }
    };

    class TestAbstract implements AbstractDto {
        @Override
        public Long getId() {
            return 567L;
        }
    }

    @Test
    public void save_idNotEqualsDtoId_throwException() {
        exceptionRule.expect(UnsupportedOperationException.class);
        service.save(999l, new TestAbstract());
    }
}

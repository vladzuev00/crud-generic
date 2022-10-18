package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.domain.dto.Car;
import by.nhorushko.crudgeneric.domain.dto.User;
import by.nhorushko.crudgeneric.domain.entity.CarEntity;
import by.nhorushko.crudgeneric.domain.entity.UserEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperExtDtoEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class AbsServiceExtCRUDTest {

    @Mock
    private AbsMapperExtDtoEntity<UserEntity, User, Long, CarEntity> mockedMapper;

    @Mock
    private JpaRepository<UserEntity, Long> mockedRepository;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<UserEntity>> userEntitiesArgumentCaptor;

    private AbsServiceExtCRUD<Long, UserEntity, User, Long, CarEntity, JpaRepository<UserEntity, Long>> service;

    @Before
    public void initializeService() {
        this.service = new AbsServiceExtCRUD<>(this.mockedMapper, this.mockedRepository) {
        };
    }

    @Test
    public void userShouldBeSaved() {
        final Long givenCarId = 255L;
        final User givenUserToBeSaved = new User(null, "email@mail.ru", "name", "surname",
                "patronymic", new Car(givenCarId, "number"));

        final UserEntity givenUserEntityToBeSaved = new UserEntity(null, "email@mail.ru", "name",
                "surname", "patronymic", new CarEntity(givenCarId, "number"));
        when(this.mockedMapper.revMap(anyLong(), any(User.class))).thenReturn(givenUserEntityToBeSaved);

        final UserEntity givenSavedUserEntity = new UserEntity(256L, "email@mail.ru", "name",
                "surname", "patronymic", new CarEntity(givenCarId, "number"));
        when(this.mockedRepository.save(any(UserEntity.class))).thenReturn(givenSavedUserEntity);

        final User givenSavedUser = new User(256L, "email@mail.ru", "name",
                "surname", "patronymic", new Car(givenCarId, "number"));
        when(this.mockedMapper.map(any(UserEntity.class))).thenReturn(givenSavedUser);

        final User actual = this.service.save(givenCarId, givenUserToBeSaved);
        assertSame(givenSavedUser, actual);

        verify(this.mockedMapper, times(1))
                .revMap(this.longArgumentCaptor.capture(), this.userArgumentCaptor.capture());
        verify(this.mockedRepository, times(1)).save(this.userEntityArgumentCaptor.capture());
        verify(this.mockedMapper, times(1)).map(this.userEntityArgumentCaptor.capture());

        assertSame(givenCarId, this.longArgumentCaptor.getValue());
        assertSame(givenUserToBeSaved, this.userArgumentCaptor.getValue());

        final List<UserEntity> expectedCapturedUserEntityArguments = List.of(
                givenUserEntityToBeSaved, givenSavedUserEntity);
        assertEquals(expectedCapturedUserEntityArguments, this.userEntityArgumentCaptor.getAllValues());
    }

    @Test
    public void usersShouldBeSaved() {
        final Long givenCarId = 255L;
        final Car givenCar = new Car(givenCarId, "number");
        final List<User> givenUsersToBeSaved = List.of(
                new User(null, "first-email@mail.ru", "firstName", "firstSurname",
                        "firstPatronymic", givenCar),
                new User(null, "second-email@mail.ru", "secondName", "secondSurname",
                        "secondPatronymic", givenCar));

        final CarEntity givenCarEntity = new CarEntity(255L, "number");
        final List<UserEntity> givenUserEntitiesToBeSaved = List.of(
                new UserEntity(null, "first-email@mail.ru", "firstName", "firstSurname",
                        "firstPatronymic", givenCarEntity),
                new UserEntity(null, "second-email@mail.ru", "secondName", "secondSurname",
                        "secondPatronymic", givenCarEntity));
        when(this.mockedMapper.revMap(anyLong(), any(User.class)))
                .thenReturn(givenUserEntitiesToBeSaved.get(0))
                .thenReturn(givenUserEntitiesToBeSaved.get(1));

        final List<UserEntity> givenSavedUserEntities = List.of(
                new UserEntity(256L, "first-email@mail.ru", "firstName", "firstSurname",
                        "firstPatronymic", givenCarEntity),
                new UserEntity(257L, "second-email@mail.ru", "secondName", "secondSurname",
                        "secondPatronymic", givenCarEntity));
        when(this.mockedRepository.saveAll(anyCollectionOf(UserEntity.class))).thenReturn(givenSavedUserEntities);

        final List<User> givenSavedUsers =  List.of(
                new User(256L, "first-email@mail.ru", "firstName", "firstSurname",
                        "firstPatronymic", givenCar),
                new User(257L, "second-email@mail.ru", "secondName", "secondSurname",
                        "secondPatronymic", givenCar));
        when(this.mockedMapper.map(anyCollectionOf(UserEntity.class))).thenReturn(givenSavedUsers);

        final List<User> actual = this.service.saveAll(givenCarId, givenUsersToBeSaved);
        assertSame(givenSavedUsers, actual);

        verify(this.mockedMapper, times(2))
                .revMap(this.longArgumentCaptor.capture(), this.userArgumentCaptor.capture());
        verify(this.mockedRepository, times(1))
                .saveAll(this.userEntitiesArgumentCaptor.capture());
        verify(this.mockedMapper, times(1))
                .map(this.userEntitiesArgumentCaptor.capture());

        assertSame(givenCarId, this.longArgumentCaptor.getValue());

        final List<List<UserEntity>> expectedCapturedUserEntitiesArguments = List.of(
                givenUserEntitiesToBeSaved, givenSavedUserEntities);
        assertEquals(expectedCapturedUserEntitiesArguments, this.userEntitiesArgumentCaptor.getAllValues());
    }
}

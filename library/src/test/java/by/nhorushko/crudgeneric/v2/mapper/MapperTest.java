package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.domain.dto.Car;
import by.nhorushko.crudgeneric.domain.dto.User;
import by.nhorushko.crudgeneric.domain.entity.CarEntity;
import by.nhorushko.crudgeneric.domain.entity.UserEntity;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public final class MapperTest {
    private final Mapper<UserEntity, User> userMapper;

    public MapperTest() {
        final ModelMapper modelMapper = new ModelMapper();
        final Mapper<CarEntity, Car> carMapper = new CarMapper(modelMapper);
        this.userMapper = new UserMapper(modelMapper, carMapper);
    }

    @Test
    public void userEntityShouldBeMappedToDto() {
        final UserEntity givenEntity = new UserEntity(255L, "email@mail.ru", "name", "surname",
                "patronymic", new CarEntity(256L, "number"));

        final User actual = this.userMapper.createDto(givenEntity);
        final User expected = new User(255L, "email@mail.ru", "name", "surname",
                "patronymic", new Car(256L, "number"));

        assertEquals(expected, actual);
    }

    private static final class CarMapper extends Mapper<CarEntity, Car> {

        public CarMapper(ModelMapper modelMapper) {
            super(modelMapper, CarEntity.class, Car.class);
        }

        @Override
        protected Car createDto(CarEntity entity) {
            return new Car(entity.getId(), entity.getNumber());
        }
    }

    private static final class UserMapper extends Mapper<UserEntity, User> {
        private final Mapper<CarEntity, Car> carMapper;

        public UserMapper(ModelMapper modelMapper, Mapper<CarEntity, Car> carMapper) {
            super(modelMapper, UserEntity.class, User.class);
            this.carMapper = carMapper;
        }

        @Override
        protected User createDto(UserEntity entity) {
            return new User(entity.getId(), entity.getEmail(), entity.getName(), entity.getSurname(),
                    entity.getPatronymic(), this.carMapper.toDto(entity.getCar()));
        }
    }
}

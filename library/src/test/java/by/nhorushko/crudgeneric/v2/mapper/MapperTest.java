package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import lombok.*;
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @EqualsAndHashCode
    @ToString
    private static final class UserEntity implements AbstractEntity {
        private Long id;
        private String email;
        private String name;
        private String surname;
        private String patronymic;
        private CarEntity car;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @EqualsAndHashCode
    @ToString
    private static final class CarEntity implements AbstractEntity {
        private Long id;
        private String number;
    }

    @Value
    private static class User implements AbstractDto {
        Long id;
        String email;
        String name;
        String surname;
        String patronymic;
        Car car;
    }

    @Value
    private static class Car implements AbstractDto {
        Long id;
        String number;
    }

    private static final class CarMapper extends Mapper<CarEntity, Car> {

        public CarMapper(ModelMapper modelMapper) {
            super(modelMapper, CarEntity.class, Car.class);
        }

        @Override
        protected Car createDto(CarEntity entity) {
            return new Car(entity.id, entity.number);
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
            return new User(entity.id, entity.email, entity.name, entity.surname, entity.patronymic,
                    this.carMapper.toDto(entity.car));
        }
    }
}

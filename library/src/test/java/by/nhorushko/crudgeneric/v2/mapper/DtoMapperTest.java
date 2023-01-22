//package by.nhorushko.crudgeneric.v2.mapper;
//
//import by.nhorushko.crudgeneric.domain.dto.Car;
//import by.nhorushko.crudgeneric.domain.dto.User;
//import by.nhorushko.crudgeneric.domain.entity.CarEntity;
//import by.nhorushko.crudgeneric.domain.entity.UserEntity;
//import org.junit.Test;
//import org.modelmapper.ModelMapper;
//
//import static org.junit.Assert.assertEquals;
//
//public final class DtoMapperTest {
//    private final AbsMapperDto<UserEntity, User> userMapper;
//
//    public DtoMapperTest() {
//        final ModelMapper modelMapper = new ModelMapper();
//        final AbsMapperDto<CarEntity, Car> carMapper = new CarAbsMapperDto(modelMapper);
//        this.userMapper = new UserAbsMapperDto(modelMapper);
//    }
//
//    @Test
//    public void userEntityShouldBeMappedToDto() {
//        final UserEntity givenEntity = new UserEntity(255L, "email@mail.ru", "name", "surname",
//                "patronymic", new CarEntity(256L, "number"));
//
//        final User actual = this.userMapper.create(givenEntity);
//        final User expected = new User(255L, "email@mail.ru", "name", "surname",
//                "patronymic", new Car(256L, "number"));
//
//        assertEquals(expected, actual);
//    }
//
//    private static final class CarAbsMapperDto extends AbsMapperDto<CarEntity, Car> {
//
//        public CarAbsMapperDto(ModelMapper modelMapper) {
//            super(modelMapper, CarEntity.class, Car.class);
//        }
//
//        @Override
//        protected Car create(CarEntity from) {
//            return new Car(from.getId(), from.getNumber());
//        }
//    }
//
//    private static final class UserAbsMapperDto extends AbsMapperDto<UserEntity, User> {
//
//
//        public UserAbsMapperDto(ModelMapper modelMapper) {
//            super(modelMapper, UserEntity.class, User.class);
//
//        }
//
//        @Override
//        protected User create(UserEntity from) {
//            return new User(from.getId(), from.getEmail(), from.getName(), from.getSurname(),
//                    from.getPatronymic(), this.map(from.getCar(), Car.class));
//        }
//    }
//}

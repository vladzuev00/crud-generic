package by.nhorushko.crudgeneric.v2.mapper;

import by.nhorushko.crudgeneric.domain.dto.Car;
import by.nhorushko.crudgeneric.domain.dto.Message;
import by.nhorushko.crudgeneric.domain.dto.Message.GpsCoordinate;
import by.nhorushko.crudgeneric.domain.entity.CarEntity;
import by.nhorushko.crudgeneric.domain.entity.MessageEntity;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public final class DtoEntityMapperTest {
    private final AbsMapperEntityDto<CarEntity, Car> carMapper;
    private final AbsMapperEntityDto<MessageEntity, Message> messageMapper;

    public DtoEntityMapperTest() {
        final ModelMapper modelMapper = new ModelMapper();
        this.carMapper = new CarAbsMapperEntityDto(modelMapper);
        this.messageMapper = new MessageAbsMapperEntityDto(modelMapper);
    }

    @Test
    public void entityWithoutSpecificFieldsShouldBeMapped() {
        final Car givenCar = new Car(255L, "number");

        final CarEntity actual = this.carMapper.toEntity(givenCar);
        final CarEntity expected = new CarEntity(255L, "number");
        assertEquals(expected, actual);
    }

    @Test
    public void entityWithSpecificFieldShouldBeMapped() {
        final Message givenMessage = new Message(255L, new GpsCoordinate(5.5F, 6.5F),
                10, 11, 12);

        final MessageEntity actual = this.messageMapper.toEntity(givenMessage);
        final MessageEntity expected = new MessageEntity(255L, 5.5F, 6.5F, 10, 11,
                12);
        assertEquals(expected, actual);
    }

    private static final class CarAbsMapperEntityDto extends AbsMapperEntityDto<CarEntity, Car> {
        public CarAbsMapperEntityDto(ModelMapper modelMapper) {
            super(modelMapper, CarEntity.class, Car.class);
        }

        @Override
        protected Car create(CarEntity from) {
            return null;
        }
    }

    private static final class MessageAbsMapperEntityDto extends AbsMapperEntityDto<MessageEntity, Message> {

        public MessageAbsMapperEntityDto(ModelMapper modelMapper) {
            super(modelMapper, MessageEntity.class, Message.class);
        }

        @Override
        protected Message create(MessageEntity from) {
            return null;
        }

        @Override
        protected void mapSpecificFields(Message source, MessageEntity destination) {
            final GpsCoordinate gpsCoordinate = source.getGpsCoordinate();
            destination.setLatitude(gpsCoordinate.getLatitude());
            destination.setLongitude(gpsCoordinate.getLongitude());
        }
    }
}

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
    private final DtoEntityMapper<CarEntity, Car> carMapper;
    private final DtoEntityMapper<MessageEntity, Message> messageMapper;

    public DtoEntityMapperTest() {
        final ModelMapper modelMapper = new ModelMapper();
        this.carMapper = new CarDtoEntityMapper(modelMapper);
        this.messageMapper = new MessageDtoEntityMapper(modelMapper);
    }

    @Test
    public void entityWithoutSpecificFieldsShouldBeMapped() {
        final Car givenCar = new Car(255L, "number");

        final CarEntity actual = this.carMapper.revMap(givenCar);
        final CarEntity expected = new CarEntity(255L, "number");
        assertEquals(expected, actual);
    }

    @Test
    public void entityWithSpecificFieldShouldBeMapped() {
        final Message givenMessage = new Message(255L, new GpsCoordinate(5.5F, 6.5F),
                10, 11, 12);

        final MessageEntity actual = this.messageMapper.revMap(givenMessage);
        final MessageEntity expected = new MessageEntity(255L, 5.5F, 6.5F, 10, 11,
                12);
        assertEquals(expected, actual);
    }

    private static final class CarDtoEntityMapper extends DtoEntityMapper<CarEntity, Car> {
        public CarDtoEntityMapper(ModelMapper modelMapper) {
            super(modelMapper, CarEntity.class, Car.class);
        }

        @Override
        protected Car create(CarEntity entity) {
            return null;
        }
    }

    private static final class MessageDtoEntityMapper extends DtoEntityMapper<MessageEntity, Message> {

        public MessageDtoEntityMapper(ModelMapper modelMapper) {
            super(modelMapper, MessageEntity.class, Message.class);
        }

        @Override
        protected Message create(MessageEntity entity) {
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

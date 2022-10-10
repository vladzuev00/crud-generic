package by.nhorushko.crudgeneric.domain.dto;

import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import lombok.Value;

@Value
public class Message implements AbstractDto<Long> {
    Long id;
    GpsCoordinate gpsCoordinate;
    int speed;
    int course;
    int altitude;

    @Value
    public static class GpsCoordinate {
        float latitude;
        float longitude;
    }
}

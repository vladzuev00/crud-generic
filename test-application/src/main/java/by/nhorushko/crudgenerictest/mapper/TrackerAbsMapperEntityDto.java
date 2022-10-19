package by.nhorushko.crudgenerictest.mapper;

import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import by.nhorushko.crudgenerictest.domain.dto.Tracker;
import by.nhorushko.crudgenerictest.domain.entity.TrackerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class TrackerAbsMapperEntityDto extends AbsMapperEntityDto<TrackerEntity, Tracker> {
    public TrackerAbsMapperEntityDto(ModelMapper modelMapper) {
        super(modelMapper, TrackerEntity.class, Tracker.class);
    }

    @Override
    protected Tracker create(TrackerEntity from) {
        return new Tracker(from.getId(), from.getImei(), from.getPhoneNumber());
    }
}

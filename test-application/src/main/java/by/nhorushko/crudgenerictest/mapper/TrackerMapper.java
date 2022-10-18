package by.nhorushko.crudgenerictest.mapper;

import by.nhorushko.crudgeneric.v2.mapper.AbstractMapper;
import by.nhorushko.crudgenerictest.domain.dto.Tracker;
import by.nhorushko.crudgenerictest.domain.entity.TrackerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public final class TrackerMapper extends AbstractMapper<TrackerEntity, Tracker> {
    public TrackerMapper(ModelMapper modelMapper) {
        super(modelMapper, TrackerEntity.class, Tracker.class);
    }

    @Override
    protected Tracker create(TrackerEntity entity) {
        return new Tracker(entity.getId(), entity.getImei(), entity.getPhoneNumber());
    }
}

package by.nhorushko.crudgenerictest.service;

import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import by.nhorushko.crudgenerictest.domain.dto.Tracker;
import by.nhorushko.crudgenerictest.domain.entity.TrackerEntity;
import by.nhorushko.crudgenerictest.mapper.TrackerAbsMapperEntityDto;
import by.nhorushko.crudgenerictest.repository.TrackerRepository;
import org.springframework.stereotype.Service;

@Service
public final class TrackerServiceCRUD extends AbsServiceCRUD<Long, TrackerEntity, Tracker, TrackerRepository> {
    public TrackerServiceCRUD(TrackerAbsMapperEntityDto mapper, TrackerRepository repository) {
        super(mapper, repository);
    }
}

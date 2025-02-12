package by.nhorushko.crudgenerictest.service;

import by.nhorushko.crudgeneric.v2.service.AbstractCrudService;
import by.nhorushko.crudgenerictest.domain.dto.Tracker;
import by.nhorushko.crudgenerictest.domain.entity.TrackerEntity;
import by.nhorushko.crudgenerictest.mapper.TrackerMapper;
import by.nhorushko.crudgenerictest.repository.TrackerRepository;
import org.springframework.stereotype.Service;

@Service
public final class TrackerService extends AbstractCrudService<Long, TrackerEntity, Tracker> {
    public TrackerService(TrackerMapper mapper, TrackerRepository repository) {
        super(mapper, repository);
    }
}

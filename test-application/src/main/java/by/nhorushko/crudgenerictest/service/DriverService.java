package by.nhorushko.crudgenerictest.service;

import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityExtDto;
import by.nhorushko.crudgeneric.v2.service.AbsServiceExtCRUD;
import by.nhorushko.crudgenerictest.domain.dto.Driver;
import by.nhorushko.crudgenerictest.domain.entity.DriverEntity;
import by.nhorushko.crudgenerictest.domain.entity.UserEntity;
import by.nhorushko.crudgenerictest.repository.DriverRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverService extends AbsServiceExtCRUD<Long, DriverEntity, Driver, Long, UserEntity, DriverRepository> {
    public DriverService(AbsMapperEntityExtDto<DriverEntity, Driver, Long, UserEntity> mapper, DriverRepository repository) {
        super(mapper, repository);
    }
}

package by.nhorushko.crudgeneric.v2.controller;

import by.nhorushko.crudgeneric.domain.SettingsVoid;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbstractRUDService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractReadUpdateController<
        ID,
        DTO extends AbstractDto<ID>,
        DTO_VIEW extends DTO,
        SETTINGS extends SettingsVoid,
        SERVICE extends AbstractRUDService<ID, ?, DTO, ?, ?>>
        extends AbstractReadController<ID, DTO, DTO_VIEW, SETTINGS, SERVICE> {

    public AbstractReadUpdateController(SERVICE service) {
        super(service);
    }

    @PutMapping("{id}")
    public ResponseEntity<DTO_VIEW> update(@PathVariable("id") Long id,
                                           SETTINGS settings,
                                           @RequestBody DTO obj,
                                           HttpServletRequest request) {
        if (!id.equals(obj.getId())) {
            throw new IllegalArgumentException("wrong id");
        }
        checkAccessUpdateBefore(obj, request);
        DTO saved = service.update(obj);
        return okResponse(saved, settings);
    }

    protected void checkAccessUpdateBefore(DTO obj, HttpServletRequest request) {
    }
}
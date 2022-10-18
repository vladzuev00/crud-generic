package by.nhorushko.crudgeneric.v2.controller;

import by.nhorushko.crudgeneric.domain.SettingsVoid;
import by.nhorushko.crudgeneric.exception.AuthenticationException;
import by.nhorushko.crudgeneric.v2.domain.AbstractDto;
import by.nhorushko.crudgeneric.v2.service.AbstractExtCRUDService;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractExtCRUDController<
        ID,
        DTO extends AbstractDto<ID>,
        DTO_VIEW extends DTO,
        SETTINGS extends SettingsVoid,
        RELATION_ID,
        SERVICE extends AbstractExtCRUDService<ID, ?, DTO, RELATION_ID, ?, ?>>
        extends AbstractReadUpdateDeleteController<ID, DTO, DTO_VIEW, SETTINGS, SERVICE> {

    public AbstractExtCRUDController(SERVICE service) {
        super(service);
    }

    /**
     * Example mapping
     * /mechanism/unit/{id}
     */
    public ResponseEntity<DTO_VIEW> save(RELATION_ID relationId,
                                         DTO body,
                                         SETTINGS settings,
                                         HttpServletRequest request) {
        checkAccessSaveBefore(relationId, body, request);
        body = handleBeforeSave(relationId, body, request);
        DTO saved = service.save(relationId, body);
        return okResponse(saved, settings);
    }

    private DTO handleBeforeSave(RELATION_ID relationId, DTO body, HttpServletRequest request) {
        return body;
    }

    protected abstract void checkAccessSaveBefore(RELATION_ID relationId, DTO obj, HttpServletRequest request)
            throws AuthenticationException;
}

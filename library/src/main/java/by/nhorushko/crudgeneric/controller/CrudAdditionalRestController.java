package by.nhorushko.crudgeneric.controller;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AuthenticationException;
import by.nhorushko.crudgeneric.service.CrudAdditionalGenericService;
import by.nhorushko.filterspecification.FilterSpecificationAbstract;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
public abstract class CrudAdditionalRestController<
        DTO extends AbstractDto, ENTITY extends AbstractEntity,
        CRUD_SERVICE extends CrudAdditionalGenericService<DTO, ENTITY, ?, ?>>
        extends RudGenericRestController<DTO, ENTITY, CRUD_SERVICE> {

    public CrudAdditionalRestController(CRUD_SERVICE service, FilterSpecificationAbstract<ENTITY> filterSpecs) {
        super(service, filterSpecs);
    }

    /** Example mapping
     * /mechanism/unit/{id}
     */
    public ResponseEntity<DTO> save(Long rootId,
                                    DTO body,
                                    HttpServletRequest request) {
        checkAccessSaveBefore(body, request);
        DTO saved = service.save(rootId, body);
        return ResponseEntity.ok(saved);
    }

    protected void checkAccessSaveBefore(DTO obj, HttpServletRequest request) throws AuthenticationException {
        checkAccess(request, obj);
    }
}

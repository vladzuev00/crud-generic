package by.nhorushko.crudgeneric.controller;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AuthenticationException;
import by.nhorushko.crudgeneric.service.CrudGenericService;
import by.nhorushko.filterspecification.FilterSpecificationAbstract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public abstract class CrudGenericRestController<
        DTO extends AbstractDto, ENTITY extends AbstractEntity,
        CRUD_SERVICE extends CrudGenericService<DTO, ENTITY, ?, ?>>
        extends RudGenericRestController<DTO, ENTITY, CRUD_SERVICE> {

    public CrudGenericRestController(CRUD_SERVICE service, FilterSpecificationAbstract<ENTITY> filterSpecs) {
        super(service, filterSpecs);
    }

    @PostMapping
    public ResponseEntity<DTO> save(@RequestBody DTO obj, HttpServletRequest request) {
        checkAccessSaveBefore(obj, request);
        DTO saved = service.save(obj);
        return ResponseEntity.ok(saved);
    }

    protected void checkAccessSaveBefore(DTO obj, HttpServletRequest request) throws AuthenticationException {
        checkAccess(request, obj);
    }
}

package by.nhorushko.crudgeneric.controller;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AuthenticationException;
import by.nhorushko.crudgeneric.service.CrudExpandGenericService;
import by.nhorushko.filterspecification.FilterSpecificationAbstract;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public abstract class CrudExpandRestController<
        DTO extends AbstractDto, ENTITY extends AbstractEntity,
        CRUD_SERVICE extends CrudExpandGenericService<DTO, ENTITY, ?, ?>>
        extends CrudAdditionalRestController<DTO, ENTITY, CRUD_SERVICE> {

    public CrudExpandRestController(CRUD_SERVICE service, FilterSpecificationAbstract<ENTITY> filterSpecs) {
        super(service, filterSpecs);
    }

    @Override
    /**
     * use update instead
     */
    public ResponseEntity<DTO> save(Long rootId, DTO body, HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void checkAccessSaveBefore(Long rootId, DTO obj, HttpServletRequest request) throws AuthenticationException {
        // do nothing
    }
}

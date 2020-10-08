package by.nhorushko.crudgeneric.controller;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AuthenticationException;
import by.nhorushko.crudgeneric.service.CrudGenericService;
import by.nhorushko.filterspecification.FilterSpecificationAbstract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CrudGenericRestController<
        DTO extends AbstractDto, ENTITY extends AbstractEntity,
        CRUD_SERVICE extends CrudGenericService<DTO, ENTITY, ?, ?>> extends PageableGenericRestController<DTO, ENTITY, CRUD_SERVICE> {

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

    @PutMapping("{id}")
    public ResponseEntity<DTO> update(@PathVariable("id") Long id,
                                      @RequestBody DTO obj,
                                      HttpServletRequest request) {

        if (!id.equals(obj.getId())) {
            throw new IllegalArgumentException("wrong id");
        }
        checkAccessUpdateBefore(obj, request);
        DTO saved = service.update(obj);
        return ResponseEntity.ok(saved);
    }

    protected void checkAccessUpdateBefore(DTO obj, HttpServletRequest request) {
        checkAccess(request, obj);
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id, HttpServletRequest request) {
        checkAccessDeleteBefore(id, request);
        service.deleteById(id);
    }

    protected void checkAccessDeleteBefore(Long id, HttpServletRequest request) {
        DTO dto = service.getById(id);
        checkAccess(request, dto);
    }
}

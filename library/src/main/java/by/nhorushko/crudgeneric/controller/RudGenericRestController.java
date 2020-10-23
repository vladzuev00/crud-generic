package by.nhorushko.crudgeneric.controller;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.service.RudGenericService;
import by.nhorushko.filterspecification.FilterSpecificationAbstract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public class RudGenericRestController<
        DTO extends AbstractDto, ENTITY extends AbstractEntity,
        CRUD_SERVICE extends RudGenericService<DTO, ENTITY, ?, ?>> extends PageableGenericRestController<DTO, ENTITY, CRUD_SERVICE> {

    public RudGenericRestController(CRUD_SERVICE service, FilterSpecificationAbstract<ENTITY> filterSpecs) {
        super(service, filterSpecs);
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
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        checkAccessDeleteBefore(id, request);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected void checkAccessDeleteBefore(Long id, HttpServletRequest request) {
        DTO dto = service.getById(id);
        checkAccess(request, dto);
    }
}

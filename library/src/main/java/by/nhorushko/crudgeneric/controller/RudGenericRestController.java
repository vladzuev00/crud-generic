package by.nhorushko.crudgeneric.controller;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.domain.SettingsVoid;
import by.nhorushko.crudgeneric.service.RudGenericService;
import by.nhorushko.filterspecification.FilterSpecificationAbstract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public abstract class RudGenericRestController<
        DTO_INTERMEDIATE extends AbstractDto,
        DTO_VIEW extends AbstractDto,
        ENTITY extends AbstractEntity,
        SETTINGS extends SettingsVoid,
        CRUD_SERVICE extends RudGenericService<DTO_INTERMEDIATE, ENTITY, ?, ?>>
        extends PageableGenericRestController<DTO_INTERMEDIATE, DTO_VIEW, ENTITY, SETTINGS, CRUD_SERVICE> {

    public RudGenericRestController(CRUD_SERVICE service, FilterSpecificationAbstract<ENTITY> filterSpecs) {
        super(service, filterSpecs);
    }

    @PutMapping("{id}")
    public ResponseEntity<DTO_VIEW> update(@PathVariable("id") Long id,
                                      SETTINGS settings,
                                      @RequestBody DTO_INTERMEDIATE obj,
                                      HttpServletRequest request) {

        if (!id.equals(obj.getId())) {
            throw new IllegalArgumentException("wrong id");
        }
        checkAccessUpdateBefore(obj, request);
        DTO_INTERMEDIATE saved = service.update(obj);
        return okResponse(saved, settings);
    }

    protected void checkAccessUpdateBefore(DTO_INTERMEDIATE obj, HttpServletRequest request) {
        checkAccess(request, obj);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        checkAccessDeleteBefore(id, request);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected void checkAccessDeleteBefore(Long id, HttpServletRequest request) {
        DTO_INTERMEDIATE dto = service.getById(id);
        checkAccess(request, dto);
    }
}

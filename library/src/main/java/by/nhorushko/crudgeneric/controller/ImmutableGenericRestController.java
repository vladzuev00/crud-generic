package by.nhorushko.crudgeneric.controller;

import by.nhorushko.crudgeneric.domain.AbstractDto;
import by.nhorushko.crudgeneric.domain.AbstractEntity;
import by.nhorushko.crudgeneric.exception.AuthenticationException;
import by.nhorushko.crudgeneric.service.CrudGenericService;
import by.nhorushko.crudgeneric.service.ImmutableGenericService;
import by.nhorushko.filterspecification.FilterSpecificationAbstract;
import by.nhorushko.filterspecification.PageRequestBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public abstract class ImmutableGenericRestController<
        DTO extends AbstractDto,
        CRUD_SERVICE extends ImmutableGenericService<DTO, ?, ?, ?>> {

    protected CRUD_SERVICE service;

    public ImmutableGenericRestController(CRUD_SERVICE service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<DTO> getById(@PathVariable("id") Long id, HttpServletRequest request) {
        checkAccessGetByIdBefore(id, request);
        DTO dto = service.getById(id);
        checkAccessGetByIdAfter(dto, request);
        return ResponseEntity.ok(dto);
    }

    protected void checkAccessGetByIdBefore(Long id, HttpServletRequest request) throws AuthenticationException {
    }

    protected void checkAccessGetByIdAfter(DTO dto, HttpServletRequest request) throws AuthenticationException {
        checkAccess(request, dto);
    }

    /**
     * ovveride если логика защиты одинакова для всех эндпоинтов
     */
    protected void checkAccess(HttpServletRequest request, DTO dto) throws AuthenticationException {

    }
}

package by.nhorushko.crudgenerictest.controller;

import by.nhorushko.crudgeneric.controller.CrudGenericRestController;
import by.nhorushko.crudgeneric.exception.AuthenticationException;
import by.nhorushko.crudgenerictest.MockService;
import by.nhorushko.crudgenerictest.domain.dto.MockADto;
import by.nhorushko.crudgenerictest.domain.entity.MockAEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RequestMapping("/mock")
@RestController
public class MockController extends CrudGenericRestController<MockADto, MockAEntity, MockService> {

    public MockController(MockService service) {
        super(service, null);
    }

    @Override
    protected void checkAccess(HttpServletRequest request, MockADto dto) throws AuthenticationException {
        super.checkAccess(request, dto);
    }
}

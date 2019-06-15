package learningapp.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.TestApi;
import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.CreationTestDto;
import learningapp.services.TestService;

import static learningapp.mappers.GeneralMapper.uuidFromString;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController implements TestApi {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID addTest(@RequestBody @Valid CreationTestDto creationTestDto) {
        return testService.addTest(creationTestDto);
    }

    @Override
    @GetMapping("/subject/{subjectId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<BaseTestDto> getTests(@PathVariable String subjectId) {
        return testService.getTests(uuidFromString(subjectId));
    }

}

package learningapp.controllers;


import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.TestApi;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.services.TestService;

@RestController(value = "TestController")
@RequestMapping(path = "/test")
public class TestController implements TestApi {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @Override
    @PostMapping("/topic/{topicId}/question")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createQuestion(@PathVariable UUID topicId, @RequestBody @Valid TestQuestionDto questionDto) {
        return testService.addTestQuestion(topicId, questionDto);
    }

}

package learningapp.controllers;


import learningapp.apis.QuestionApi;
import learningapp.dtos.question.TableQuestionDto;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static learningapp.mappers.GeneralMapper.uuidFromString;

@RestController(value = "QuestionController")
@RequestMapping(path = "/topic")
@CrossOrigin
class QuestionController implements QuestionApi {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    @PostMapping("/{topicId}/question")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createQuestion(@PathVariable String topicId, @RequestBody @Valid TestQuestionDto questionDto) {
        return questionService.addTestQuestion(uuidFromString(topicId), questionDto);
    }

    @Override
    @PostMapping("/{topicId}/question/{questionId}")
    public UUID updateQuestion(@PathVariable String topicId, @RequestBody @Valid TestQuestionDto questionDto) {
        return questionService.updateTestQuestion(uuidFromString(topicId), questionDto);
    }

    @Override
    @PutMapping("/{topicId}/question/{questionId}")
    public void validateQuestion(@PathVariable String topicId, @RequestBody @Valid TestQuestionDto questionDto) {
        questionService.validateQuestion(questionDto);
    }

    @Override
    @GetMapping("/{topicId}/questions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TableQuestionDto> getAllPendingQuestionsByTopic(@PathVariable String topicId) {
        return questionService.getAllQuestionsByTopic(uuidFromString(topicId));
    }

    @Override
    @GetMapping("/question/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TestQuestionDto getQuestion(@PathVariable String id) {
        return questionService.getQuestion(uuidFromString(id));
    }

}

package learningapp.controllers;


import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.QuestionApi;
import learningapp.dtos.question.TableQuestionDto;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.services.QuestionService;

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
    @GetMapping("/professor/{professorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TableQuestionDto> getAllQuestionsForProfessor(@PathVariable String professorId) {
        return questionService.getAllQuestionForProfessor(uuidFromString(professorId));
    }

    @Override
    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TableQuestionDto> getAllQuestionsForStudent(@PathVariable String studentId) {
        return questionService.getAllQuestionForStudent(uuidFromString(studentId));
    }

    @Override
    @GetMapping("/notificationsCount/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public int getNotificationsCount(@PathVariable String userId) {
        return questionService.getNotificationsCount(uuidFromString(userId));
    }

    @Override
    @GetMapping("/question/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TestQuestionDto getQuestion(@PathVariable String id) {
        return questionService.getQuestion(uuidFromString(id));
    }

}

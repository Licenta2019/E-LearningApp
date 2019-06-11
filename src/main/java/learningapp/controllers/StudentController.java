package learningapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.StudentApi;
import learningapp.dtos.question.TableQuestionDto;
import learningapp.services.QuestionService;

import static learningapp.mappers.GeneralMapper.uuidFromString;


@RestController(value = "StudentController")
@RequestMapping(path = "/student")
@CrossOrigin
@PreAuthorize("hasAuthority(STUDENT)")
public class StudentController implements StudentApi {

    private final QuestionService questionService;

    public StudentController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    @GetMapping("/{studentId}/questions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TableQuestionDto> getAllQuestions(@PathVariable String studentId) {
        return questionService.getAllQuestionForStudent(uuidFromString(studentId));
    }

}

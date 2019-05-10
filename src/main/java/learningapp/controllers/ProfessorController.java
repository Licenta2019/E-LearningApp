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

import learningapp.apis.ProfessorApi;
import learningapp.dtos.question.TableQuestionDto;
import learningapp.services.QuestionService;

import static learningapp.mappers.GeneralMapper.uuidFromString;

@RestController(value = "ProfessorController")
@RequestMapping(path = "/professor")
@CrossOrigin
//@PreAuthorize("hasAuthority(PROFESSOR)")
public class ProfessorController implements ProfessorApi {

    private final QuestionService questionService;

    public ProfessorController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    @GetMapping("/{professorId}/questions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TableQuestionDto> getAllQuestions(@PathVariable String professorId) {
        return questionService.getAllQuestionForProfessor(uuidFromString(professorId));
    }

}

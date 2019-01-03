package learningapp.controllers;


import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learningapp.apis.ProfessorApi;
import learningapp.dtos.SubjectDto;
import learningapp.services.SubjectService;

@RestController(value = "ProfessorController")
@RequestMapping(path = "/professor")
public class ProfessorController implements ProfessorApi {

    @Autowired
    private final SubjectService subjectService;

    public ProfessorController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    @PostMapping("/subject")
    public UUID createSubject(@RequestBody @Valid SubjectDto subjectDto) {
        return subjectService.addSubject(subjectDto);
    }

}

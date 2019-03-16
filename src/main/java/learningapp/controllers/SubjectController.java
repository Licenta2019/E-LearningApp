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

import learningapp.apis.SubjectApi;
import learningapp.dtos.subject.SubjectDto;
import learningapp.dtos.subject.TopicDto;
import learningapp.services.SubjectService;

import static learningapp.mappers.GeneralMapper.uuidFromString;

@RestController(value = "ProfessorController")
@RequestMapping(path = "/subject")
@CrossOrigin
public class SubjectController implements SubjectApi {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UUID addSubject(@RequestBody @Valid SubjectDto subjectDto) {
        return subjectService.addSubject(subjectDto);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{subjectId}/topic")
    public UUID addTopicToSubject(@PathVariable String subjectId, @RequestBody @Valid TopicDto topicDto) {
        return subjectService.addTopicToSubject(uuidFromString(subjectId), topicDto);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{subjectId}")
    public SubjectDto getSubject(@PathVariable String subjectId) {
        return subjectService.getSubject(uuidFromString(subjectId));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<SubjectDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{subjectId}")
    public UUID updateSubject(@PathVariable String subjectId, @RequestBody @Valid SubjectDto subjectDto) {
        return subjectService.updateSubject(uuidFromString(subjectId), subjectDto);
    }

}

package learningapp.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import learningapp.dtos.subject.SubjectDto;
import learningapp.entities.Subject;
import learningapp.exceptions.base.DuplicateEntityException;
import learningapp.repositories.SubjectRepository;

import static learningapp.exceptions.ExceptionMessages.DUPLICATE_SUBJECT;

@Component
public class SubjectValidator {

    private final SubjectRepository subjectRepository;

    public SubjectValidator(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public void validateSubject(SubjectDto subjectDto) {
        Optional<Subject> optionalSubject = subjectRepository.findByName(subjectDto.getName());

        if (optionalSubject.isPresent()) {
            throw new DuplicateEntityException(DUPLICATE_SUBJECT);
        }
    }

}

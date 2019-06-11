package learningapp.manager;

import java.util.UUID;

import org.springframework.stereotype.Service;

import learningapp.dtos.notification.NotificationCreationDto;
import learningapp.dtos.question.TestQuestionDto;
import learningapp.repositories.ProfessorRepository;
import learningapp.services.NotificationService;
import learningapp.services.QuestionService;
import learningapp.services.impl.QuestionServiceImpl;

import static learningapp.entities.notification.NotificationType.INFO;
import static learningapp.mappers.NotificationMapper.toNotificationCreationDto;

@Service
public class NotificationManagerImpl implements NotificationManager {

    private final NotificationService notificationService;

    private final QuestionService questionService;

    private final ProfessorRepository professorRepository;

    public NotificationManagerImpl(NotificationService notificationService, QuestionServiceImpl questionService, ProfessorRepository professorRepository) {
        this.notificationService = notificationService;
        this.questionService = questionService;
        this.professorRepository = professorRepository;
    }

    @Override
    public UUID addQuestionAndNotify(UUID topicId, String username, TestQuestionDto testQuestionDto) {
        UUID testQuestionId = questionService.addTestQuestion(topicId, username, testQuestionDto);

        NotificationCreationDto notificationDto = toNotificationCreationDto("New notification from", INFO,
                professorRepository.findAllByTopic(topicId));

        notificationService.saveNotification(notificationDto);

        return testQuestionId;
    }

}

package learningapp.manager;

import java.util.UUID;

import learningapp.dtos.question.TestQuestionDto;

public interface NotificationManager {

    UUID addQuestionAndNotify(UUID topicId, String usernae, TestQuestionDto testQuestionDto);

}

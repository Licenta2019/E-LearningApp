package learningapp.apis;

import java.util.List;
import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import learningapp.dtos.question.TableQuestionDto;
import learningapp.dtos.question.TestQuestionDto;

@Api(tags = "Question API")
public interface QuestionApi {

    UUID createQuestion(@ApiParam(value = "id of the related topic", required = true) String topicId,
                        @ApiParam(value = "question data", required = true) TestQuestionDto questionDto);

    UUID updateQuestion(@ApiParam(value = "id of the related topic", required = true) String topicId,
                        @ApiParam(value = "question data", required = true) TestQuestionDto questionDto);

    void validateQuestion(@ApiParam(value = "id of the related topic", required = true) String topicId,
                          @ApiParam(value = "question data", required = true) TestQuestionDto questionDto);

    List<TableQuestionDto> getAllPendingQuestionsByTopic(@ApiParam(value = "id of the related topic", required = true) String topicId);

    int getNotificationsCount(@ApiParam(value = "user to be notified data", required = true) String userId);

    TestQuestionDto getQuestion(@ApiParam(value = "id of the question", required = true) String id);

}
package learningapp.apis;

import java.util.List;
import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import learningapp.dtos.question.TestQuestionDto;

@Api(tags = "Question API", description = "The API for questions")
public interface QuestionApi {

    UUID createQuestion(@ApiParam(value = "id of the related topic", required = true) String topicId,
                        @ApiParam(value = "question data", required = true) TestQuestionDto questionDto);

    UUID updateQuestion(@ApiParam(value = "id of the related topic", required = true) String topicId,
                        @ApiParam(value = "question data", required = true) TestQuestionDto questionDto);

    void validateQuestion(@ApiParam(value = "id of the related topic", required = true) String topicId,
    @ApiParam(value = "question data", required = true) TestQuestionDto questionDto);

    List<TestQuestionDto> getAllPendingQuestionsByTopic(@ApiParam(value = "id of the related topic", required = true) String topicId);

    TestQuestionDto getQuestion(@ApiParam(value = "id of the question", required = true) String id);

}

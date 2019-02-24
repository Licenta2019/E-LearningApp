package learningapp.apis;

import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import learningapp.dtos.question.TestQuestionDto;

@Api(tags = "Tests API", description = "The API for tests")
public interface TestApi {

    UUID createQuestion(@ApiParam(value = "id of the related topic", required = true) String topicId,
                        @ApiParam(value = "question data", required = true) TestQuestionDto questionDto);

    UUID updateQuestion(@ApiParam(value = "id of the related topic", required = true) String topicId,
                        @ApiParam(value = "question data", required = true) TestQuestionDto questionDto);

}

package learningapp.apis;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import learningapp.dtos.question.TableQuestionDto;


@Api(tags = "Student API", description = "The API for student")
public interface StudentApi {

    List<TableQuestionDto> getAllQuestions(@ApiParam(value = "id of the student", required = true) String userId);
}

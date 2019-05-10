package learningapp.apis;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import learningapp.dtos.question.TableQuestionDto;

@Api(tags = "Professor API", description = "The API for professor")
public interface ProfessorApi {

    List<TableQuestionDto> getAllQuestions(@ApiParam(value = "id of the professor", required = true) String userId);
}

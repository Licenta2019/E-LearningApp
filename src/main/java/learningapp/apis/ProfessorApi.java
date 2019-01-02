package learningapp.apis;

import java.util.UUID;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import learningapp.dtos.SubjectDto;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Authentication API", description = "The API for authentication")
public interface ProfessorApi {
    @ApiOperation(
            value = "User log in",
            produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    UUID createSubject(@ApiParam(value = "the subject data", required = true) @Valid SubjectDto subjectDto);

}

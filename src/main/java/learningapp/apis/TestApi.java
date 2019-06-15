package learningapp.apis;

import java.util.List;
import java.util.UUID;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import learningapp.dtos.test.BaseTestDto;
import learningapp.dtos.test.CreationTestDto;

public interface TestApi {

    @ApiOperation(
            value = "Add a subject"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 201, message = "Successful created"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    UUID addTest(@ApiParam(value = "the test data", required = true) CreationTestDto creationTestDto);

    @ApiOperation(
            value = "Add a subject"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 201, message = "Successful created"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    List<BaseTestDto> getTests(@ApiParam(value = "the subject id", required = true) String subjectId);

}

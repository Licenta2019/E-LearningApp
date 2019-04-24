package learningapp.apis;

import java.util.List;
import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import learningapp.dtos.subject.SubjectDto;
import learningapp.dtos.subject.TopicDto;

@Api(tags = "Subject API", description = "The API for subject")
public interface SubjectApi {

    @ApiOperation(
            value = "Add a subject"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 201, message = "Successful created"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    UUID addSubject(@ApiParam(value = "the subject data", required = true) SubjectDto subjectDto);

    @ApiOperation(
            value = "Add a topic"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 201, message = "Successful created"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    UUID addTopicToSubject(@ApiParam(value = "the subject id", required = true) String subjectId,
                       @ApiParam(value = "the topic data", required = true) TopicDto topicDto);

    @ApiOperation(
            value = "Get a subject"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    SubjectDto getSubject(@ApiParam(value = "the subject id", required = true) String subjectId);

    @ApiOperation(
            value = "Get all subjects"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    List<SubjectDto> getAllSubjects();

    @ApiOperation(
            value = "Update subject"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    UUID updateSubject(@ApiParam(value = "the subject id", required = true) String subjectId,
                       @ApiParam(value = "the subject data", required = true) SubjectDto subjectDto);

}

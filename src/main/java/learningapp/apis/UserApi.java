package learningapp.apis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import learningapp.dtos.user.UserDto;

@Api(tags = "User API")
public interface UserApi {

    @ApiOperation(
            value = "login"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    UserDto getUser(@ApiParam(value = "the user id", required = true) String id);

    void validatePassword(@ApiParam(value = "the user id", required = true) String id,
                          @ApiParam(value = "the user password", required = true) String password);

    @ApiOperation(
            value = "login"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    void updateUser(@ApiParam(value = "the user id", required = true) String id,
                    @ApiParam(value = "the user data", required = true) UserDto userDto);

    void register(@ApiParam(value = "the user data", required = true) UserDto userDto);

}

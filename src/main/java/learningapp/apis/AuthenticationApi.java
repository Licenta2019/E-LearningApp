package learningapp.apis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import learningapp.dtos.AuthenticationDto;
import learningapp.dtos.user.BaseUserDto;
import learningapp.dtos.user.UserDto;

@Api(tags = "Authentication API")
public interface AuthenticationApi {

    @ApiOperation(
            value = "login"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "The server cannot or will not process the request due to an apparent client error")
    })
    BaseUserDto login(@ApiParam(value = "the authentication data", required = true) AuthenticationDto authenticationDto);

}

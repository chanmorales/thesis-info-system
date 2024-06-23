package com.portfolio.mutex.tims.controller;

import static com.portfolio.mutex.tims.common.UriConstants.USER_BASE_URI;

import com.portfolio.mutex.tims.dto.PageDto;
import com.portfolio.mutex.tims.dto.UserDto;
import com.portfolio.mutex.tims.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_BASE_URI)
@Tag(name = "Users")
public class UserController {

  private final UserService userService;

  /**
   * Retrieve paginated users
   *
   * @return 200 (OK) if successful, any other response indicates a failure
   */
  @GetMapping
  public ResponseEntity<PageDto<UserDto>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  /**
   * Retrieve the current user
   *
   * @param userDetails the details of the current user
   * @return 200 (OK) if successful, any other response indicates a failure
   */
  @Operation(summary = "Retrieve current user",
      description = "Retrieve the details of the current logged in user")
  @ApiResponses(
      @ApiResponse(responseCode = "200", description = "Request is successful",
          content = @Content(mediaType = "application/json",
              examples = @ExampleObject(value = """
                  {
                    "lastname": "User",
                    "firstname": "Admin",
                    "middlename": "",
                    "email": "admin@user.com",
                    "active": true,
                    "systemAdmin": false
                  }
                  """)))
  )
  @GetMapping("/myself")
  public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    return ResponseEntity.ok(userService.getUser(userDetails.getUsername()));
  }
}

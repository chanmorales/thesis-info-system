package com.portfolio.mutex.tims.controller;

import static com.portfolio.mutex.tims.common.UriConstants.USER_BASE_URI;

import com.portfolio.mutex.tims.dto.PageDto;
import com.portfolio.mutex.tims.dto.UserDto;
import com.portfolio.mutex.tims.service.UserService;
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
  @GetMapping("/myself")
  public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    return ResponseEntity.ok(userService.getUser(userDetails.getUsername()));
  }
}

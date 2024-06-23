package com.portfolio.mutex.tims.controller;

import static com.portfolio.mutex.tims.common.UriConstants.AUTHENTICATION_BASE_URI;
import static com.portfolio.mutex.tims.common.UriConstants.LOGIN_URI;

import com.portfolio.mutex.tims.dto.JwtDto;
import com.portfolio.mutex.tims.dto.UserLoginDto;
import com.portfolio.mutex.tims.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_BASE_URI)
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  /**
   * Authenticates the user that tries to log in
   *
   * @param userLogin the details of the user that tries to log in
   * @return 200 (OK) if successful, any other response indicates failure
   */
  @Hidden
  @PostMapping(LOGIN_URI)
  public ResponseEntity<JwtDto> authenticate(@RequestBody UserLoginDto userLogin) {
    return ResponseEntity.ok(authenticationService.authenticateLogin(userLogin));
  }
}

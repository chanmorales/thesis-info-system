package com.portfolio.mutex.tims.service;

import com.portfolio.mutex.tims.dto.JwtDto;
import com.portfolio.mutex.tims.dto.UserLoginDto;

public interface AuthenticationService {

  /**
   * Authenticate the user that tries to log in
   *
   * @param userLogin the details of the user
   * @return JWT of the authenticated user
   */
  JwtDto authenticateLogin(UserLoginDto userLogin);
}

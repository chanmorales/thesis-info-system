package com.portfolio.mutex.tims.service;

import com.portfolio.mutex.tims.dto.JwtDto;
import com.portfolio.mutex.tims.dto.UserLoginDto;
import com.portfolio.mutex.tims.entity.User;
import com.portfolio.mutex.tims.exception.AppServiceException;
import com.portfolio.mutex.tims.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtService jwtService;

  /**
   * {@inheritDoc}
   */
  @Override
  public JwtDto authenticateLogin(UserLoginDto userLogin) {
    // Authenticate user
    User authenticatedUser = authenticate(userLogin);

    // Generate token and expiration
    String jwtToken = jwtService.generateToken(authenticatedUser);
    long expiration = jwtService.getExpirationTime();

    return JwtDto.builder()
        .token(jwtToken)
        .expiration(expiration)
        .build();
  }

  /**
   * Authenticates user login
   *
   * @param userLogin the details of the user that tries to login
   * @return authenticated user
   */
  private User authenticate(UserLoginDto userLogin) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));

    return userRepository.findByEmail(userLogin.getEmail())
        .orElseThrow(
            () -> new AppServiceException("Invalid credentials.", HttpStatus.UNAUTHORIZED));
  }
}

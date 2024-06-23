package com.portfolio.mutex.tims.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UriConstants {

  /*
   * Authentication Resource URI
   */
  public static final String AUTHENTICATION_BASE_URI = "/auth";
  public static final String LOGIN_URI = "/login";

  /*
   * User Resource URI
   */
  public static final String USER_BASE_URI = "/users";
  public static final String CURRENT_USER_URI = "/myself";
}

package com.portfolio.mutex.tims.service;

import com.portfolio.mutex.tims.dto.PageDto;
import com.portfolio.mutex.tims.dto.UserDto;

public interface UserService {

  PageDto<UserDto> getUsers();
  
  UserDto getUser(String email);
}

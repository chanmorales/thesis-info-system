package com.portfolio.mutex.tims.service;

import com.portfolio.mutex.tims.dto.PageDto;
import com.portfolio.mutex.tims.dto.UserDto;
import com.portfolio.mutex.tims.entity.User;
import com.portfolio.mutex.tims.exception.AppServiceException;
import com.portfolio.mutex.tims.repository.UserRepository;
import com.portfolio.mutex.tims.util.Mapper;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public PageDto<UserDto> getUsers() {
    // TODO
    PageDto<UserDto> result = PageDto.<UserDto>builder().values(Collections.emptyList()).build();
    
    return result;
  }

  @Override
  public UserDto getUser(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new AppServiceException("User not found.", HttpStatus.NOT_FOUND));
    return Mapper.map(user);
  }
}

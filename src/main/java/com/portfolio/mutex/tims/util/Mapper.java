package com.portfolio.mutex.tims.util;

import com.portfolio.mutex.tims.dto.UserDto;
import com.portfolio.mutex.tims.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mapper {

  /**
   * Maps user entity to user DTO
   *
   * @param user the user entity
   * @return the user DTO
   */
  public static UserDto map(User user) {
    return UserDto.builder()
        .lastname(user.getLastname())
        .firstname(user.getFirstname())
        .middlename(user.getMiddlename())
        .email(user.getEmail())
        .active(user.getActive())
        .systemAdmin(user.getSystemAdmin())
        .build();
  }
}

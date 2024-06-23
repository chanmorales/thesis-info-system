package com.portfolio.mutex.tims.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class UserDto {

  private String lastname;

  private String firstname;

  private String middlename;

  private String email;

  private Boolean active;

  private Boolean systemAdmin;
}

package com.portfolio.mutex.tims.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class PageDto<T> {

  private List<T> values;
}

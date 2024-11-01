package com.GGomDDakPing.QnLove.QnLove.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author : frozzun
 * @filename :QuizDto.java
 * @since 10/15/24
 */
@Getter
@Setter
@Builder
public class QuizDto {
  @NotNull(message = "content cannot be null")
  private String content;
  @NotNull(message = "answer cannot be null")
  private boolean answer;
}

package com.GGomDDakPing.QnLove.QnLove.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuizDto {
  @NotNull(message = "content cannot be null")
  private String content;
  @NotNull(message = "answer cannot be null")
  private Long answer;
}

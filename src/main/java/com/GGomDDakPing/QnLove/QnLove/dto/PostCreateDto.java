package com.GGomDDakPing.QnLove.QnLove.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * @author : frozzun
 * @filename :PostCreateDto.java
 * @since 10/15/24
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostCreateDto {
  @NotNull(message = "member cannot be null")
  private Long memberId;

  @NotNull(message = "Title cannot be null")
  private String title;

  @NotNull(message = "content cannot be null")
  private String content;

  @NotNull(message = "Quizzes cannot be null")
  @Size(min = 5, max = 5, message = "Exactly 5 quizzes must be provided")
  private List<QuizDto> quizList;
}

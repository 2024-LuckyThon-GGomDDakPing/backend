package com.GGomDDakPing.QnLove.QnLove.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreatePostDto {
  @NotNull(message = "member cannot be null")
  private Long memberId;

  @NotNull(message = "Title cannot be null")
  private String title;

  @NotNull(message = "content cannot be null")
  private String content;

  @NotNull(message = "Quizzes cannot be null")
  @Size(min = 4, max = 4, message = "Exactly 4 quizzes must be provided")
  private List<QuizDto> quizList;
}

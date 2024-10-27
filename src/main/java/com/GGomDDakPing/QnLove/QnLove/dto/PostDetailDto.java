package com.GGomDDakPing.QnLove.QnLove.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostDetailDto {
  @NotNull(message = "id cannot be null")
  private Long postId;

  @NotNull(message = "title cannot be null")
  private String title;

  @NotNull(message = "content cannot be null")
  private String content;

  @NotNull(message = "memberId cannot be null")
  private Long memberId;

  @NotNull(message = "quizList cannot be null")
  private List<QuizDto> quizDtoList;
}

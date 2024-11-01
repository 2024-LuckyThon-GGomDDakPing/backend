package com.GGomDDakPing.QnLove.QnLove.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 *
 * @author : frozzun
 * @filename :PostDetailDto.java
 * @since 10/25/24
 */
@Getter
@Setter
@Builder
public class PostDetailDto {
  @NotNull(message = "id cannot be null")
  private Long postId;

  @NotNull(message = "memberId cannot be null")
  private Long memberId;

  @NotNull(message = "title cannot be null")
  private String title;

  @NotNull(message = "content cannot be null")
  private String content;

  @NotNull(message = "profileImg cannot be null")
  private String profileImg;

  @NotNull(message = "name cannot be null")
  private String name;

  @NotNull(message = "instagramId cannot be null")
  private String instagramId;

  @NotNull(message = "sex cannot be null")
  private Long sex;

  @NotNull(message = "quizList cannot be null")
  private List<QuizDto> quizDtoList;
}

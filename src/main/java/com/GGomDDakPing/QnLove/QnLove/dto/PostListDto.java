package com.GGomDDakPing.QnLove.QnLove.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class PostListDto {
  @NotNull(message = "id cannot be null")
  private Long postId;

  @NotNull(message = "title cannot be null")
  private String title;

  @NotNull(message = "content cannot be null")
  private String content;

  @NotNull(message = "memberId cannot be null")
  private Long memberId;
}

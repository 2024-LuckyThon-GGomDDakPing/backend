package com.GGomDDakPing.QnLove.QnLove.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreatePostDTO {
  @NotNull(message = "member cannot be null")
  private Long memberId;

  @NotNull(message = "Title cannot be null")
  private String title;

  @NotNull(message = "content cannot be null")
  private String content;
}

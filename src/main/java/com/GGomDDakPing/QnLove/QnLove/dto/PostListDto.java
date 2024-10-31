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

  @NotNull(message = "memberId cannot be null")
  private Long memberId;

  @NotNull(message = "title cannot be null")
  private String title;

  @NotNull(message = "content cannot be null")
  private String content;

  @NotNull(message = "profileImg cannot be null")
  private String profileImg;

  @NotNull(message = "nickname cannot be null")
  private String nickname;

  @NotNull(message = "sex cannot be null")
  private Long sex;
}

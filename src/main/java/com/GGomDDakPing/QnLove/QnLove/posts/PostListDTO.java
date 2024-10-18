package com.GGomDDakPing.QnLove.QnLove.posts;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostListDTO {
  @NotNull(message = "id cannot be null")
  private Long postId;

  @NotNull(message = "title cannot be null")
  private String title;

  @NotNull(message = "content cannot be null")
  private String content;

  @NotNull(message = "memberId cannot be null")
  private Long memberId;
}

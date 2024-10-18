package com.GGomDDakPing.QnLove.QnLove.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostResponse {

  private Long postId;
  private Long memberId;
  private String title;
  private String content;
}

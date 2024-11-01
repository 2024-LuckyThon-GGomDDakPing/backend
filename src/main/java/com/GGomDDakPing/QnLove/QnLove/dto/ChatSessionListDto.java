package com.GGomDDakPing.QnLove.QnLove.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionListDto {
  private Long otherMemberId;
  private String profileImage;
  private String nickname;
  private String lastMessage;
}

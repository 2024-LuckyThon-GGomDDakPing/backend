package com.GGomDDakPing.QnLove.QnLove.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author : frozzun
 * @filename :ChatSessionListDto.java
 * @since 11/02/24
 */
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

package com.GGomDDakPing.QnLove.QnLove.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// ChatSession.java (채팅 세션 관리 클래스)
@Getter
@Setter
@Builder
public class ChatSession {
  private String sessionId; // 세션 ID
  private List<String> participants; // 참여자 목록
}


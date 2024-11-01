package com.GGomDDakPing.QnLove.QnLove.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author : frozzun
 * @filename :ChatSession.java
 * @since 10/29/24
 */
@Entity
@Table(name = "chat_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSession {
  @Id
  private String id; // 예를 들어, "A-B" 또는 UUID 형식으로 세션 ID를 생성할 수 있습니다.

  private Long member1Id; // 사용자 1의 ID
  private Long member2Id; // 사용자 2의 ID
}


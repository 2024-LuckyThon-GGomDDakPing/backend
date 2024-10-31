package com.GGomDDakPing.QnLove.QnLove.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long senderId;
  private Long receiverId;
  private String content;
  private LocalDateTime timestamp;

  public Message(Long senderId, Long receiverId, String content, LocalDateTime timestamp) {
    this.senderId = senderId;
    this.receiverId = receiverId;
    this.content = content;
    this.timestamp = timestamp;
  }
}

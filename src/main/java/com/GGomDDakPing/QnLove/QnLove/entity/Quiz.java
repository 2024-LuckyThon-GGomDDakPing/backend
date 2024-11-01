package com.GGomDDakPing.QnLove.QnLove.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 *
 * @author : frozzun
 * @filename :Quiz.java
 * @since 10/12/24
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "postId", nullable = false)
  private Post post;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private boolean answer;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  @Builder.Default
  private boolean isDeleted = false;

  public Quiz(Post post, String content, boolean answer) {
    this.post = post;
    this.content = content;
    this.answer = answer;
  }

}

package com.GGomDDakPing.QnLove.posts;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "postId", nullable = false)
  private Post post;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Long answer;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private boolean isDeleted;

  public Quiz(Post post, String content, Long answer) {
    this.post = post;
    this.content = content;
    this.answer = answer;
  }

}

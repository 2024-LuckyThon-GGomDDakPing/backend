package com.GGomDDakPing.QnLove.QnLove.posts;

import com.GGomDDakPing.QnLove.QnLove.members.Member;
import com.GGomDDakPing.QnLove.QnLove.quizs.Quiz;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "memberId", nullable = false)
  private Member member;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Column(nullable = false)
  private boolean isDeleted = false;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Quiz> quiz;

}

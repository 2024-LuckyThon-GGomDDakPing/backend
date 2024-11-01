package com.GGomDDakPing.QnLove.QnLove.repository;

import com.GGomDDakPing.QnLove.QnLove.entity.Post;
import com.GGomDDakPing.QnLove.QnLove.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author : frozzun
 * @filename :QuizRepository.java
 * @since 10/15/24
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
  List<Quiz> findAllByPost(Post post);
}

package com.GGomDDakPing.QnLove.QnLove.quizs;

import com.GGomDDakPing.QnLove.QnLove.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}

package com.GGomDDakPing.QnLove.QnLove.service;

import com.GGomDDakPing.QnLove.QnLove.entity.Quiz;
import com.GGomDDakPing.QnLove.QnLove.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
  private final QuizRepository quizRepository;

  @Autowired
  public QuizService(QuizRepository quizRepository) {
    this.quizRepository = quizRepository;
  }

  /**
   * 퀴즈 생성
   */
  public void createQuizzes(List<Quiz> quizzes) {
    quizRepository.saveAll(quizzes);
  }

  /**
   * 퀴즈 조회
   */


}
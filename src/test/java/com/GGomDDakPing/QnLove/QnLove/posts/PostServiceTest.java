package com.GGomDDakPing.QnLove.QnLove.posts;

import com.GGomDDakPing.QnLove.QnLove.members.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

  @Autowired
  PostRepository postRepository;

  @Autowired
  PostService postService;

  @AfterEach
  public void tearDown() {
    postRepository.deleteAll();
  }

  @Test
  void addPost() {
    //given
    Member member = Member.builder()
        .name("테스터")
        .email("eonjun0783@inu.ac.kr")
        .instagramId("wjddjswns")
        .password("qwer")
        .sex(1L)
        .age(26L)
        .isConnected(true)
        .build();

    Post post = Post.builder()
        .title("이것은 제목 입니다")
        .content("이것은 내용 입니다")
        .member(member)
        .build();
    //when
    Long saveId = postService.addPost(post);

    //then
    Post findPost = postRepository.findById(saveId).get();
    assertThat(post.getTitle()).isEqualTo(findPost.getTitle());
  }

  @Test
  void deletePost() {
  }

  @Test
  void modifyPost() {
  }

  @Test
  void getAllPosts() {
  }

  @Test
  void getPostById() {
  }
}
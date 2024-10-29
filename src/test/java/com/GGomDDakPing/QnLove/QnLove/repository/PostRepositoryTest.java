//package com.GGomDDakPing.QnLove.QnLove.repository;
//
//import com.GGomDDakPing.QnLove.QnLove.entity.Member;
//import com.GGomDDakPing.QnLove.QnLove.entity.Post;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // MySQL 사용
//class PostRepositoryTest {
//
//  @Autowired
//  private PostRepository postRepository;
//  @Autowired
//  private MemberRepository memberRepository;
//
//  @Test
//  public void save() {
//    Member member = Member.builder()
//      .name("Tester")
//      .email("tester@test.com")
//      .instagramId("tester")
//      .password("qwer")
//      .sex(1L)
//      .age(26L)
//      .build();
//
//    // Member 객체 저장
//    Member savedMember = memberRepository.save(member);
//
//    assertThat(member).isEqualTo(savedMember);
//
//    Post post = Post.builder()
//      .member(savedMember) // 저장한 Member 객체 사용
//      .title("Test Title")
//      .content("Test Content")
//      .build();
//
//    // Post 객체 저장
//    Post savedPost = postRepository.save(post);
//
//    // assertThat(post).isEqualTo(savedPost);
//
//    }
//}

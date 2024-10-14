package com.GGomDDakPing.QnLove.QnLove.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

  private final PostRepository postRepository;

  @Autowired
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }
  /**
   * 게시물 생성
   * Post
   */
  public Long addPost(Post post) {
    postRepository.save(post);
    return post.getId();
  }

  /**
   * 게시물 삭제
   * DELETE
   */
  public void deletePost(Long postId) {
    postRepository.deleteById(postId);
  }

  /**
   * 게시물 수정
   * POST
   */
  public Post modifyPost(Post post) {
    return postRepository.save(post);
  }


  /**
   * 전체 게시물 조회
   * GET
   */
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  /**
   * 특정 게시물 조회
   * GET
   */
  public Optional<Post> getPostById(Long postId) {
    checkPostExists(postId);
    return postRepository.findById(postId);
  }

  /**
   * 특정 게시물 존재 검증
   */
  private void checkPostExists(Long postId) {
    postRepository.findById(postId)
        .ifPresent(post -> {
          throw new IllegalStateException("존재하지 않는 postId.");
        });
  }

  /**
   * 게시물 존재 검증
   */
}



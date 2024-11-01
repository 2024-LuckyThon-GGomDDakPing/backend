package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.dto.PostCreateDto;
import com.GGomDDakPing.QnLove.QnLove.dto.PostDetailDto;
import com.GGomDDakPing.QnLove.QnLove.dto.QuizDto;
import com.GGomDDakPing.QnLove.QnLove.entity.Member;

import com.GGomDDakPing.QnLove.QnLove.entity.Post;
import com.GGomDDakPing.QnLove.QnLove.dto.PostListDto;
import com.GGomDDakPing.QnLove.QnLove.entity.Quiz;
import com.GGomDDakPing.QnLove.QnLove.service.MemberService;
import com.GGomDDakPing.QnLove.QnLove.service.PostService;
import com.GGomDDakPing.QnLove.QnLove.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
  private PostService postService;
  private QuizService quizService;
  private MemberService memberService;


  /**
   *
   * @author : frozzun
   * @filename :PostController.java
   * @since 10/15/24
   */
  @Autowired
  public PostController(PostService postService, MemberService memberService, QuizService quizService) {
    this.postService = postService;
    this.memberService = memberService;
    this.quizService = quizService;
  }

  /**
   * 게시물 생성 API
   * @author frozzun
   * @param postCreateDto createPostDTO
   */
  @PostMapping
  @ResponseBody
  @Operation(
    summary = "게시물 생성",
    description = "게시물을 생성할 때 사용하는 API",
    requestBody = @RequestBody(
      description = "post data to create",
      required = true,
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = PostCreateDto.class)
      )
    )
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "게시물 생성 성공"),
    @ApiResponse(responseCode = "404", description = "")
  })
  public void createPost(@org.springframework.web.bind.annotation.RequestBody PostCreateDto postCreateDto) {
    //add debug log
    System.out.println("PostCreateDto received: MemberId = " + postCreateDto.getMemberId() + ", Title = " + postCreateDto.getTitle() + ", Content = " + postCreateDto.getContent());

    //매핑 확인
    if (postCreateDto.getTitle() == null || postCreateDto.getContent() == null || postCreateDto.getMemberId() == null) {
      throw new IllegalArgumentException("Invalid input data: MemberId or Title or Content is null");
    }

    Member member = memberService.getMemberById(postCreateDto.getMemberId());
    Post post = Post.builder()
      .member(member)
      .title(postCreateDto.getTitle())
      .content(postCreateDto.getContent())
      .build();

    Long Id = postService.addPost(post);
    //Post id log
    System.out.println("Created post id = " + Id);

    List<QuizDto> quizListDto = postCreateDto.getQuizList();

    // QuizDto 리스트를 Quiz 리스트로 변환 (빌더 사용)
    List<Quiz> quizList = quizListDto.stream()
      .map(quizDto -> Quiz.builder()
        .post(post)
        .content(quizDto.getContent())
        .answer(quizDto.isAnswer())
        .build())
      .toList();
    quizService.createQuizzes(quizList);
  }

  /**
   * 게시물 삭제 API
   * @author frozzun
   * @param postId 글 아이디
   */
  @DeleteMapping("/{postId}")
  @Operation(
    summary = "게시물 삭제",
    description = "게시물을 삭제할 때 사용하는 API"
  )
  @Parameter(name = "postId", description = "글 Id", example = "1")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "게시물 삭제 성공"),
    @ApiResponse(responseCode = "404", description = "")
  })
  public void deletePost(@Parameter(description = "ID of the post to be deleted") @PathVariable Long postId) {
      postService.deletePost(postId);
  }

  /**
   * 전체 게시물 조회 API
   * @author frozzun
   */
  @GetMapping
  @Operation(
    summary = "전체 게시물 조회",
    description = "전체 게시물을 조회 할 때 사용하는 API"
  )
  public List<PostListDto> getAllPosts() {
    List<Post> postList = postService.getAllPosts();

    return postList.stream().map(post -> PostListDto.builder()
      .postId(post.getId())
      .memberId(post.getMember().getId())
      .title(post.getTitle())
      .content(post.getContent())
      .profileImg(post.getMember().getProfileImage())
      .nickname(post.getMember().getNickname())
      .sex(post.getMember().getSex())
      .build()).toList();
  }


  /**
   * 특정 게시물 조회 API
   * @author frozzun
   * @param postId 글 아이디
   */
  @GetMapping("/{postId}")
  @Operation(
    summary = "특정 게시물 조회",
    description = "특정 게시물을 조회할 때 사용하는 API"
  )
  @Parameter(name = "postId", description = "글 Id", example = "1")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "게시물 조회 성공"),
    @ApiResponse(responseCode = "404", description = "")
  })
  public PostDetailDto getPost(@PathVariable Long postId) {
    Post post = postService.getPostById(postId).get();

    List<Quiz> quizList = quizService.getQuizListByPost(post);

    List<QuizDto> quizListDto = quizList.stream()
      .map(quiz -> QuizDto.builder()
        .content(quiz.getContent())
        .answer(quiz.isAnswer())
        .build())
      .toList();


    return PostDetailDto.builder()
      .postId(post.getId())
      .memberId(post.getMember().getId())
      .title(post.getTitle())
      .content(post.getContent())
      .profileImg(post.getMember().getProfileImage())
      .name(post.getMember().getName())
      .instagramId(post.getMember().getInstagramId())
      .sex(post.getMember().getSex())
      .quizDtoList(quizListDto)
      .build();
  }
}

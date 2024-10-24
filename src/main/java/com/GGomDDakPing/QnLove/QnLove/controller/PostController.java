package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.dto.CreatePostDto;
import com.GGomDDakPing.QnLove.QnLove.dto.QuizDto;
import com.GGomDDakPing.QnLove.QnLove.entity.Member;

import com.GGomDDakPing.QnLove.QnLove.entity.Post;
import com.GGomDDakPing.QnLove.QnLove.dto.PostListDto;
import com.GGomDDakPing.QnLove.QnLove.entity.Quiz;
import com.GGomDDakPing.QnLove.QnLove.service.PostService;
import com.GGomDDakPing.QnLove.QnLove.repository.MemberRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
  private PostService postService;
  private QuizService quizService;
//  private MemberService memberService;

  //임시 코드
  @Autowired
  private MemberRepository memberRepository;


  @Autowired
  public PostController(PostService postService, QuizService quizService) {
    this.postService = postService;
    this.quizService = quizService;
  }

//  public PostController(PostService postService, MemberService memberService, QuizService quizService) {
//    this.postService = postService;
//    this.memberService = memberService;
//    this.quizService = quizService;
//  }

  /**
   * 게시물 생성 API
   * @author frozzun
   * @param createPostDto createPostDTO
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
          schema = @Schema(implementation = CreatePostDto.class)
      )
    )
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "게시물 생성 성공"),
    @ApiResponse(responseCode = "404", description = "")
  })
  public void createPost(@org.springframework.web.bind.annotation.RequestBody CreatePostDto createPostDto) {
    //add debug log
    System.out.println("CreatePostDto received: MemberId = " + createPostDto.getMemberId() + ", Title = " + createPostDto.getTitle() + ", Content = " + createPostDto.getContent());

    //매핑 확인
    if (createPostDto.getTitle() == null || createPostDto.getContent() == null || createPostDto.getMemberId() == null) {
      throw new IllegalArgumentException("Invalid input data: MemberId or Title or Content is null");
    }

    //임시 코드
    Optional<Member> OPmember = memberRepository.findById(createPostDto.getMemberId());
//    Optional<Member> OPmember = memberService.findById(createPostDto.getMemberId());

    if (OPmember.isPresent()) {
      Member member = OPmember.get();
      Post post = Post.builder()
        .member(member)
        .title(createPostDto.getTitle())
        .content(createPostDto.getContent())
        .build();

      Long Id = postService.addPost(post);
      //Post id log
      System.out.println("Created post id = " + Id);

      List<QuizDto> quizDtoList = createPostDto.getQuizList();

      // QuizDto 리스트를 Quiz 리스트로 변환 (빌더 사용)
      List<Quiz> quizList = quizDtoList.stream()
        .map(quizDto -> Quiz.builder()
          .post(post)
          .content(quizDto.getContent())
          .answer(quizDto.getAnswer())
          .build())
        .toList();

      quizService.createQuizzes(quizList);

    } else {
      throw new IllegalArgumentException("Member with ID " + createPostDto.getMemberId() + " not found");
    }
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
   * 게시물 수정 API
   */


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
    List<Post> posts = postService.getAllPosts();

    return posts.stream().map(post -> {
      return PostListDto.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .memberId(post.getMember().getId())
        .build();
    }).toList();
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
  public PostListDto getPost(@PathVariable Long postId) {
    Post post = postService.getPostById(postId).get();

    return PostListDto.builder()
      .postId(post.getId())
      .title(post.getTitle())
      .content(post.getContent())
      .memberId(post.getMember().getId())
      .build();
  }


}

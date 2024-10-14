package com.GGomDDakPing.QnLove.QnLove.posts;

import com.GGomDDakPing.QnLove.QnLove.members.Member;

import com.GGomDDakPing.QnLove.QnLove.members.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class PostController {
  private PostService postService;
//  private MemberService memberService;

  //임시 코드
  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

//  public PostController(PostService postService, MemberService memberService) {
//    this.postService = postService;
//    this.memberService = memberService;
//  }

  /**
   * 게시물 생성 API
   */
  @PostMapping(value = "posts/create")
  @ResponseBody
  @Operation(
    summary = "게시물 생성",
    description = "게시물을 생성할 때 사용하는 API",
    requestBody = @RequestBody(
      description = "post data to create",
      required = true,
      content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = CreatePostDTO.class)
      )
    )
  )
  public void createPost(@org.springframework.web.bind.annotation.RequestBody CreatePostDTO createPostDto) {
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
    } else {
      throw new IllegalArgumentException("Member with ID " + createPostDto.getMemberId() + " not found");
    }
  }

  /**
   * 게시물 삭제 API
   */


  /**
   * 게시물 수정 API
   */


  /**
   * 전체 게시물 조회 API
   */


  /**
   * 특정 게시물 조회 API
   */


}

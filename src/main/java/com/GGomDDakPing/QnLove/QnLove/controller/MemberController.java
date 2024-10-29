package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.dto.PostRegisterDto;
import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import com.GGomDDakPing.QnLove.QnLove.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(
            summary = "회원 가입",
            description = "회원 가입을 수행하는 API",
            requestBody = @RequestBody(
                    description = "회원가입에 필요한 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PostRegisterDto.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "409", description = "중복된 데이터")
    })
    @PostMapping("/register")
    public void  postRegister(@Valid @org.springframework.web.bind.annotation.RequestBody  PostRegisterDto postRegisterDto) {
        System.out.println("=== DTO 값 확인 ===");
        System.out.println("loginId: " + postRegisterDto.getLoginId());
        System.out.println("password: " + postRegisterDto.getPassword());
        System.out.println("name: " + postRegisterDto.getName());
        System.out.println("email: " + postRegisterDto.getEmail());
        System.out.println("sex: " + postRegisterDto.getSex());
        System.out.println("age: " + postRegisterDto.getAge());
        System.out.println("instagramId: " + postRegisterDto.getInstagramId());
        System.out.println("profileImage: " + postRegisterDto.getProfileImage());

        Member member = postRegisterDto.toEntity();

        // Entity 각 필드 값 출력
        System.out.println("=== Entity 값 확인 ===");
        System.out.println("loginId: " + member.getLoginId());
        System.out.println("password: " + member.getPassword());
        System.out.println("name: " + member.getName());
        System.out.println("email: " + member.getEmail());
        System.out.println("sex: " + member.getSex());
        System.out.println("age: " + member.getAge());
        System.out.println("instagramId: " + member.getInstagramId());
        System.out.println("profileImage: " + member.getProfileImage());

        memberService.registerMember(member);
    }
}

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
    public void  postRegister(@RequestBody @Valid PostRegisterDto postRegisterDto) {
        Member member1 = Member.builder()
                .loginId(postRegisterDto.getLoginId())
                .password(postRegisterDto.getPassword())
                .name(postRegisterDto.getName())
                .email(postRegisterDto.getEmail())
                .instagramId(postRegisterDto.getInstagramId())
                .sex(postRegisterDto.getSex())
                .age(postRegisterDto.getAge())
                .build();
        // Member member = postRegisterDto.toEntity();
        // Member registeredMember =
        memberService.registerMember(member1);
//     // return ResponseEntity.status(200).body(registeredMember);
    }
}

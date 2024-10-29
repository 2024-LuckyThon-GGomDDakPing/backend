package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.dto.MemberLoginDto;
import com.GGomDDakPing.QnLove.QnLove.dto.PostRegisterDto;
import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import com.GGomDDakPing.QnLove.QnLove.exceptional.Exceptionals;
import com.GGomDDakPing.QnLove.QnLove.repository.MemberRepository;
import com.GGomDDakPing.QnLove.QnLove.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    public static Hashtable<String, HttpSession> sessionList = new Hashtable<>();


    @Autowired
    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
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

        Member member = postRegisterDto.toEntity();

        memberService.registerMember(member);
    }
    @Operation(
            summary = "로그인",
            description = "로그인 API",
            requestBody = @RequestBody(
                    description = "로그인",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberLoginDto.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
    })
    @PostMapping("/login")
    public void postLogin(@Valid @org.springframework.web.bind.annotation.RequestBody MemberLoginDto pld, HttpServletRequest httpServletRequest) {
        Optional<Member> member = memberRepository.findByLoginId(pld.getLoginId());
        if (member.isEmpty()) {
            throw new Exceptionals("존재하지 않는 아이디입니다.");
        }
        Member user = member.get();
        if(!user.getPassword().equals(pld.getPassword())) {
            throw new Exceptionals("비밀번호가 일치하지 않습니다.");
        }
        user.setConnected(true);
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", user.getId());
        session.setMaxInactiveInterval(1800);
        sessionList.put(session.getId(), session);
        memberRepository.save(user);
    }
    @Operation(
            summary = "로그아웃",
            description = "로그아웃 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
    })
    @PostMapping("/logout")
    public void logout(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember != null) {
            loginMember.setConnected(false);
            memberRepository.save(loginMember);
            sessionList.remove(session.getId());
        }
        session.invalidate();
    }

    @Operation(
            summary = "접속중인 사용자목록",
            description = "접속중인 사용자목록 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
    })
    @GetMapping("/session-list")
    public Map<String, String> sessionList() {
        Enumeration elements = sessionList.elements();
        Map<String, String> lists = new HashMap<>();
        while(elements.hasMoreElements()) {
            HttpSession session = (HttpSession)elements.nextElement();
            lists.put(session.getId(), String.valueOf(session.getAttribute("userId")));
        }
        return lists;
    }


}

package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.dto.MemberLoginDto;
import com.GGomDDakPing.QnLove.QnLove.dto.MemberRegisterDto;
import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import com.GGomDDakPing.QnLove.QnLove.repository.MemberRepository;
import com.GGomDDakPing.QnLove.QnLove.service.MemberService;
import com.GGomDDakPing.QnLove.QnLove.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;
    public static Hashtable<String, HttpSession> sessionList = new Hashtable<>();


    @Autowired
    public MemberController(MemberService memberService, MemberRepository memberRepository, S3Service s3Service) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.s3Service = s3Service;
    }

    @Operation(
            summary = "회원 가입",
            description = "회원 가입을 수행하는 API",
            requestBody = @RequestBody(
                    description = "회원가입에 필요한 데이터",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = MemberRegisterDto.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "409", description = "중복된 데이터")
    })
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public void postRegister(
      @Valid @ModelAttribute MemberRegisterDto memberRegisterDto,
      @RequestPart MultipartFile photo) {
      Member member;
      try {
        member = Member.builder()
          .name(memberRegisterDto.getName())
          .profileImage(s3Service.uploadImage(photo))
          .nickname(memberRegisterDto.getNickname())
          .instagramId(memberRegisterDto.getInstagramId())
          .password(memberRegisterDto.getPassword())
          .loginId(memberRegisterDto.getLoginId())
          .sex(memberRegisterDto.getSex())
          .age(memberRegisterDto.getAge())
          .build();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

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
        Member user = memberService.loginMember(pld.getLoginId(), pld.getPassword());
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
        Map<String, String> lists = new HashMap<>();

        // Create a copy of session IDs to avoid concurrent modification
        List<String> sessionIds = new ArrayList<>(sessionList.keySet());

        for (String sessionId : sessionIds) {
            try {
                HttpSession session = sessionList.get(sessionId);
                if (session != null) {
                    Object userId = session.getAttribute("userId");
                    if (userId != null) {
                        lists.put(sessionId, String.valueOf(userId));
                    }
                }
            } catch (IllegalStateException e) {
                // Remove invalid session
                sessionList.remove(sessionId);
            }
        }

        return lists;
    }

    @Operation(
            summary = "회원 정보 조회",
            description = "회원 정보 조회 API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원"),
    })
    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @Scheduled(fixedRate = 900000) // Run every 15 minutes
    public void cleanupSessions() {
        List<String> sessionIds = new ArrayList<>(sessionList.keySet());
        for (String sessionId : sessionIds) {
            try {
                HttpSession session = sessionList.get(sessionId);
                if (session == null || session.getAttribute("userId") == null) {
                    sessionList.remove(sessionId);
                }
            } catch (IllegalStateException e) {
                sessionList.remove(sessionId);
            }
        }
    }

}

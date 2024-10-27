package com.GGomDDakPing.QnLove.QnLove.service;

import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import com.GGomDDakPing.QnLove.QnLove.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member registerMember(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (memberRepository.findByName(member.getName()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이름입니다.");
        }
        if (memberRepository.findByInstagramId(member.getInstagramId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 인스타그램 ID입니다.");
        }
        return memberRepository.save(member);
    }
}

package com.GGomDDakPing.QnLove.QnLove.service;

import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import com.GGomDDakPing.QnLove.QnLove.exceptional.Exceptionals;
import com.GGomDDakPing.QnLove.QnLove.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void registerMember(Member member) {
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new Exceptionals("이미 사용 중인 이메일입니다.");
        }
        if (memberRepository.findByName(member.getName()).isPresent()) {
            throw new Exceptionals("이미 사용 중인 이름입니다.");
        }
        if (memberRepository.findByInstagramId(member.getInstagramId()).isPresent()) {
            throw new Exceptionals("이미 사용 중인 인스타그램 ID입니다.");
        }
        memberRepository.save(member);
    }
}

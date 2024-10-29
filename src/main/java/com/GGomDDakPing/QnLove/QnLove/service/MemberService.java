package com.GGomDDakPing.QnLove.QnLove.service;

import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import com.GGomDDakPing.QnLove.QnLove.exceptional.Exceptionals;
import com.GGomDDakPing.QnLove.QnLove.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


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

    public Member loginMember(String loginId, String password) {
        Optional<Member> member = memberRepository.findByLoginId(loginId);
        if (member.isEmpty()) {
            throw new Exceptionals("존재하지 않는 아이디입니다.");
        }
        Member user = member.get();
        if (!user.getPassword().equals(password)) {
            throw new Exceptionals("비밀번호가 일치하지 않습니다.");
        }
        user.setConnected(true);
        memberRepository.save(user);
        return user;
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new Exceptionals("존재하지 않는 회원입니다."));
    }
}

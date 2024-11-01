package com.GGomDDakPing.QnLove.QnLove.repository;

import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findById(long memberId);
    Optional<Member> findByNickname(String Nickname);
    Optional<Member> findByName(String name);
    Optional<Member> findByInstagramId(String instagramId);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByPassword(String password);
}

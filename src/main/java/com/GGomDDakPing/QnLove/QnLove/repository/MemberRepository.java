package com.GGomDDakPing.QnLove.QnLove.repository;

import com.GGomDDakPing.QnLove.QnLove.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}

package com.GGomDDakPing.QnLove.QnLove.repository;

import com.GGomDDakPing.QnLove.QnLove.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatSessionRepository extends JpaRepository<ChatSession, String> {
  @Query("SELECT c FROM ChatSession c WHERE c.member1Id = :memberId OR c.member2Id = :memberId")
  List<ChatSession> findByMemberId(@Param("memberId") Long memberId);

  @Query("SELECT CASE WHEN c.member1Id = :memberId THEN c.member2Id ELSE c.member1Id END FROM ChatSession c WHERE c.id = :chatSessionId")
  Long findOtherMemberIdByChatSessionId(@Param("chatSessionId") String chatSessionId, @Param("memberId") Long memberId);


}

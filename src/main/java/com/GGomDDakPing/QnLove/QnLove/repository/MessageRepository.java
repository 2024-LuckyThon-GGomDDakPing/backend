package com.GGomDDakPing.QnLove.QnLove.repository;

import com.GGomDDakPing.QnLove.QnLove.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

  @Query("SELECT m FROM Message m WHERE (m.senderId = :userId1 AND m.receiverId = :userId2) OR (m.senderId = :userId2 AND m.receiverId = :userId1) ORDER BY m.timestamp ASC")
  List<Message> findChatHistory(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

}

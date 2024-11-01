package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.dto.ChatSessionListDto;
import com.GGomDDakPing.QnLove.QnLove.entity.ChatSession;
import com.GGomDDakPing.QnLove.QnLove.entity.Message;
import com.GGomDDakPing.QnLove.QnLove.service.ChatService;
import com.GGomDDakPing.QnLove.QnLove.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author : frozzun
 * @filename :ChatController.java
 * @since 10/31/24
 */
@Slf4j
@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatService chatService;
  private final RabbitTemplate rabbitTemplate;
  private final MemberService memberService;

  @Autowired
  public ChatController(ChatService chatService, RabbitTemplate rabbitTemplate, MemberService memberService) {
    this.chatService = chatService;
    this.rabbitTemplate = rabbitTemplate;
    this.memberService = memberService;
  }

  // 메시지 전송
  @PostMapping("/send")
  public void sendMessage(@RequestParam Long senderId,
                          @RequestParam Long receiverId,
                          @RequestParam String content) {
    chatService.sendMessage(senderId, receiverId, content);
  }

  @GetMapping("/{memberId}")
  public List<ChatSessionListDto> getChatSessions(@PathVariable Long memberId) {
    List<ChatSession> chatSessionList = chatService.getChatSessions(memberId);

    // chatSessionList를 순회하여 ChatSessionListDto로 변환
    return chatSessionList.stream()
      .map(chatSession -> {
        Long memId = chatService.getOtherMemberId(memberId, chatSession.getId());
        String nickName = memberService.getNicknameById(memId);
        String profileImage = memberService.getProfileImage(memId);
        String lastMessage = chatService.getLastMessage(memberId,memId);

        return ChatSessionListDto.builder()
          .otherMemberId(memId)
          .nickname(nickName)
          .profileImage(profileImage)
          .lastMessage(lastMessage)
          .build();
      })
      .collect(Collectors.toList());
  }



  // 채팅 기록 조회
  @GetMapping("/history")
  public List<Message> getChatHistory(@RequestParam Long member1Id,
                                      @RequestParam Long member2Id) {
    return chatService.getChatHistory(member1Id, member2Id);
  }

  // 채팅방 별 큐에서 수신 대기
  @GetMapping("/receive")
  public ResponseEntity<List<Message>> receiveMessages(@RequestParam Long senderId,
                                                       @RequestParam Long receiverId) {
    Optional<List<Message>> optionalMessages = chatService.getMessages(senderId, receiverId);

    // 메시지가 없을 경우 204 No Content 반환
    return optionalMessages.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
  }
}

package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.entity.Message;
import com.GGomDDakPing.QnLove.QnLove.service.ChatService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

  private final ChatService chatService;
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public ChatController(ChatService chatService, RabbitTemplate rabbitTemplate) {
    this.chatService = chatService;
    this.rabbitTemplate = rabbitTemplate;
  }

  // 메시지 전송
  @PostMapping("/send")
  public void sendMessage(@RequestParam Long senderId,
                          @RequestParam Long receiverId,
                          @RequestParam String content) {
    chatService.sendMessage(senderId, receiverId, content);
  }

  // 채팅 기록 조회
  @GetMapping("/history")
  public List<Message> getChatHistory(@RequestParam Long userId1,
                                      @RequestParam Long userId2) {
    return chatService.getChatHistory(userId1, userId2);
  }

  // 채팅방 별 큐에서 수신 대기
  @GetMapping("/receive")
  public ResponseEntity<Message> receiveMessage(@RequestParam Long senderId,
                                                @RequestParam Long receiverId) {
    String sessionId = chatService.generateSessionId(senderId, receiverId);
    String queueName = "chat_queue_" + sessionId;

    // 큐에서 메시지 수신
    Message message = (Message) rabbitTemplate.receiveAndConvert(queueName);

    if (message != null) {
      return ResponseEntity.ok(message);
    } else {
      // 메시지가 없는 경우 204 No Content 반환
      return ResponseEntity.noContent().build();
    }
  }

}

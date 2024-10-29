package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final MessageProducer messageProducer;

  @Autowired
  public ChatController(MessageProducer messageProducer) {
    this.messageProducer = messageProducer;
  }

  // 메시지 전송 API
  @PostMapping("/{sessionId}/send")
  public String sendMessage(@PathVariable String sessionId, @RequestBody Map<String, String> payload) {
    String message = payload.get("message");
    messageProducer.sendMessageToSession(sessionId, message); // 세션 ID와 함께 메시지 전송
    return "Message sent: " + message;
  }

  // 예: 채팅 세션 생성 API (옵션)
  @PostMapping("/create")
  public String createSession(@RequestBody Map<String, String> participants) {
    // 참가자 정보로 채팅 세션을 생성하는 로직을 구현
    // 예: 세션 ID 생성 후 데이터베이스에 저장
    return "Chat session created.";
  }
}

package com.GGomDDakPing.QnLove.QnLove.controller;

import com.GGomDDakPing.QnLove.QnLove.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageProducer messageProducer;

  @Autowired
  public MessageController(MessageProducer messageProducer) {
    this.messageProducer = messageProducer;
  }

  @PostMapping
  public String sendMessage(@RequestBody Map<String, String> payload) {
    String message = payload.get("message");
    messageProducer.sendMessage(message);
    return "Message sent: " + message;
  }
}


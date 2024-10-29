package com.GGomDDakPing.QnLove.QnLove.service;

import com.GGomDDakPing.QnLove.QnLove.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public MessageProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(String message) {
    rabbitTemplate.convertAndSend(
      RabbitMQConfig.EXCHANGE_NAME, "routing.key", message);
    System.out.println("Sent: " + message);
  }
}


package com.GGomDDakPing.QnLove.QnLove.service;

import com.GGomDDakPing.QnLove.QnLove.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

  @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
  public void receiveMessage(String message) {
    System.out.println("Received: " + message);
  }

  // MessageConsumer.java (메시지 소비자)
}


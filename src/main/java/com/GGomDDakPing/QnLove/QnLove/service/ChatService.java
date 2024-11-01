package com.GGomDDakPing.QnLove.QnLove.service;

import com.GGomDDakPing.QnLove.QnLove.entity.ChatSession;
import com.GGomDDakPing.QnLove.QnLove.entity.Message;
import com.GGomDDakPing.QnLove.QnLove.repository.ChatSessionRepository;
import com.GGomDDakPing.QnLove.QnLove.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChatService {

  private final RabbitTemplate rabbitTemplate;
  private final MessageRepository messageRepository;
  private final ChatSessionRepository chatSessionRepository;
  private final SimpleRabbitListenerContainerFactory listenerContainerFactory;

  @Autowired
  public ChatService(RabbitTemplate rabbitTemplate,
                     MessageRepository messageRepository,
                     ChatSessionRepository chatSessionRepository,
                     SimpleRabbitListenerContainerFactory listenerContainerFactory) {
    this.rabbitTemplate = rabbitTemplate;
    this.messageRepository = messageRepository;
    this.chatSessionRepository = chatSessionRepository;
    this.listenerContainerFactory = listenerContainerFactory;
  }

  public void sendMessage(Long senderId, Long receiverId, String content) {
    // 세션 ID 생성
    String sessionId = generateSessionId(senderId, receiverId);
    String queueName = "chat_queue_" + sessionId;

    // 채팅 세션 확인 또는 생성
    chatSessionRepository.findById(sessionId).orElseGet(() -> {
      ChatSession newSession = new ChatSession(sessionId, senderId, receiverId);
      createQueueAndListener(queueName);  // 동적 큐 및 리스너 생성
      return chatSessionRepository.save(newSession);
    });

    // 메시지 생성 및 저장
    Message message = new Message(senderId, receiverId, content, LocalDateTime.now());
    log.info("ChatService : sendMessage:  {}", message);
    messageRepository.save(message);

    rabbitTemplate.convertAndSend(queueName, message);
  }

  public String generateSessionId(Long user1, Long user2) {
    return user1.compareTo(user2) < 0 ? user1 + "-" + user2 : user2 + "-" + user1; // 정렬된 세션 ID 생성
  }

  public List<Message> getChatHistory(Long userId1, Long userId2) {
    return messageRepository.findChatHistory(userId1, userId2);
  }

  private void createQueueAndListener(String queueName) {
    try {
      // Queue를 동적으로 생성
      rabbitTemplate.getConnectionFactory().createConnection().createChannel(false)
        .queueDeclare(queueName, true, false, false, null);

      // Listener 설정
      SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
      container.setConnectionFactory(rabbitTemplate.getConnectionFactory()); // ConnectionFactory 설정
      container.setQueueNames(queueName);
      container.setMessageListener(new MessageListener() {
        @Override
        public void onMessage(org.springframework.amqp.core.Message message) {
          // 수신된 메시지를 처리하는 로직
          try {
            Message receivedMessage = (Message) rabbitTemplate.getMessageConverter().fromMessage(message);
            log.info("ChatService : receivedMessage: {}", receivedMessage);
          } catch (Exception e) {
            log.error(e.getMessage());
          }
        }
      });
      container.start(); // 리스너 컨테이너 시작
    } catch (Exception e) {
      // 예외 처리
      log.error(e.getMessage());
    }
  }

  public Optional<List<Message>> getMessages(Long senderId, Long receiverId) {
    List<Message> messages = messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    return messages.isEmpty() ? Optional.empty() : Optional.of(messages);
  }


}

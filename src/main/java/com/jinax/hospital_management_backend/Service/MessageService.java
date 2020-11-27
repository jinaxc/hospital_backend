package com.jinax.hospital_management_backend.Service;

import com.jinax.hospital_management_backend.Entity.Message;
import com.jinax.hospital_management_backend.Repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : chara
 */
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message){
        message.setId(null);
        return messageRepository.save(message);
    }

    public List<Message> getMessageForUser(Long userId){
        return messageRepository.findByTargetIdEquals(userId);
    }
}

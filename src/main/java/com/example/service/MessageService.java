package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

import java.util.*;

import com.example.repository.AccountRepository;


@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message){
        Account user = accountRepository.findUserById(message.getPostedBy());
        if (user == null || message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create message");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageGivenMessageId(int messageId){
        Message message = messageRepository.getMessageGivenMessageId(messageId);
        return message;
    }

    public int deleteMessageGivenMessageId(int messageId){
        if(messageRepository.getMessageGivenMessageId(messageId) != null){
            messageRepository.deleteMessageGivenMessageId(messageId);
            return 1;
        }
        return 0;
    }
}

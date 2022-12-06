package com.mochi.service.chat.impl;

import com.mochi.model.chat.*;
import com.mochi.model.user.User;
import com.mochi.repository.chat.IChatBoxDetailsMessagesRepository;
import com.mochi.repository.chat.IChatBoxDetailsRepository;
import com.mochi.repository.chat.IChatBoxRepository;
import com.mochi.repository.chat.IMessageRepository;
import com.mochi.repository.user.IUserRepository;
import com.mochi.service.chat.IChatBoxService;
import com.mochi.service.chat.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.mochi.model.chat.ChatBoxStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements IChatService {
    private final IChatBoxDetailsRepository chatBoxDetailsRepository;
    private final IUserRepository userRepository;
    private final IChatBoxRepository chatBoxRepository;
    private final IMessageRepository messageRepository;
    private final IChatBoxDetailsMessagesRepository chatBoxDetailsMessagesRepository;

    @Override
    public Message sendMessageToChatBox(User user, String messageContent, Long idChatBox) {
        Optional<ChatBox> chatBox = chatBoxRepository.findById(idChatBox);
        if (!chatBox.isPresent()) {
            return null;
        }
        Message message = messageRepository.saveAndFlush(new Message(messageContent, user));
        List<ChatBoxDetails> details = chatBoxDetailsRepository.findByChatBox(chatBox.get());
        for (ChatBoxDetails detail : details) {
            chatBoxDetailsMessagesRepository.save(new ChatBoxDetailsMessages(detail, message));
            if (!detail.getUser().getUsername().equals(user.getUsername())) {
                detail.setStatus(UNREAD);
            }
        }
        return message;
    }

    @Override
    public void addUserToChatBox(Long idUser, Long idChatBox) {
        Optional<User> user = userRepository.findById(idUser);
        Optional<ChatBox> chatBox = chatBoxRepository.findById(idChatBox);
        if (!user.isPresent() || !chatBox.isPresent()) {
            return;
        }
        Optional<ChatBoxDetails> find = chatBoxDetailsRepository.findByChatBoxAndUser(chatBox.get(), user.get());
        if (find.isPresent()) {
            return;
        }
        chatBoxDetailsRepository.save(new ChatBoxDetails(user.get(), chatBox.get()));
    }

    @Override
    public void deleteMessageAtYourChatBoxDetails(Long idMessage, Long idChatBoxDetails) {
        Optional<ChatBoxDetails> chatBoxDetails = chatBoxDetailsRepository.findById(idChatBoxDetails);
        Optional<Message> message = messageRepository.findById(idMessage);
        if (!chatBoxDetails.isPresent() || !message.isPresent()) {
            return;
        }
        chatBoxDetailsMessagesRepository.deleteByChatBoxDetailsAndMessage(chatBoxDetails.get(), message.get());
    }

    @Override
    public void retrieveMessage(Long idMessage) {
        Optional<Message> message = messageRepository.findById(idMessage);
        if (!message.isPresent()) {
            return;
        }
        messageRepository.deleteById(idMessage);
    }

    @Override
    public boolean setChatBoxStatus(Long idChatBox, ChatBoxStatus status) {
        Optional<ChatBoxDetails> detail = chatBoxDetailsRepository.findById(idChatBox);
        if (!detail.isPresent()) {
            return false;
        }
        detail.get().setStatus(status);
        return true;
    }
}

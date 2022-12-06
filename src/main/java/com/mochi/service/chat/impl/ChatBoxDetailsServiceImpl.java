package com.mochi.service.chat.impl;

import com.mochi.model.chat.ChatBox;
import com.mochi.model.chat.ChatBoxDetails;
import com.mochi.model.chat.ChatBoxStatus;
import com.mochi.model.user.User;
import com.mochi.repository.chat.IChatBoxDetailsRepository;
import com.mochi.service.chat.IChatBoxDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.mochi.model.chat.ChatBoxStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatBoxDetailsServiceImpl implements IChatBoxDetailsService {
    private final IChatBoxDetailsRepository chatBoxDetailsRepository;

    @Override
    public List<ChatBoxDetails> findAll() {
        return chatBoxDetailsRepository.findAll();
    }

    @Override
    public Page<ChatBoxDetails> findAll(Pageable pageable) {
        return chatBoxDetailsRepository.findAll(pageable);
    }

    @Override
    public Optional<ChatBoxDetails> findById(Long id) {
        return chatBoxDetailsRepository.findById(id);
    }

    @Override
    public ChatBoxDetails save(ChatBoxDetails chatBoxDetails) {
        return chatBoxDetailsRepository.save(chatBoxDetails);
    }

    @Override
    public void remove(Long id) {
        chatBoxDetailsRepository.deleteById(id);
    }

    @Override
    public List<ChatBoxDetails> findByChatBox(ChatBox chatBox) {
        return chatBoxDetailsRepository.findByChatBox(chatBox);
    }

    @Override
    public List<ChatBoxDetails> findByUser(User user) {
        return chatBoxDetailsRepository.findByUser(user);
    }

}

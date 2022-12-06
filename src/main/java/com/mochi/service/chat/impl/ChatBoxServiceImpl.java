package com.mochi.service.chat.impl;

import com.mochi.model.chat.ChatBox;
import com.mochi.repository.chat.IChatBoxRepository;
import com.mochi.service.chat.IChatBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatBoxServiceImpl implements IChatBoxService {
    private final IChatBoxRepository chatBoxRepository;

    @Override
    public List<ChatBox> findAll() {
        return chatBoxRepository.findAll();
    }

    @Override
    public Page<ChatBox> findAll(Pageable pageable) {
        return chatBoxRepository.findAll(pageable);
    }

    @Override
    public Optional<ChatBox> findById(Long id) {
        return chatBoxRepository.findById(id);
    }

    @Override
    public ChatBox save(ChatBox chatBox) {
        return chatBoxRepository.save(chatBox);
    }

    @Override
    public void remove(Long id) {
        chatBoxRepository.deleteById(id);
    }
}

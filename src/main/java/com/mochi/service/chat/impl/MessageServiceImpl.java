package com.mochi.service.chat.impl;

import com.mochi.model.chat.Message;
import com.mochi.repository.chat.IMessageRepository;
import com.mochi.service.chat.IMessageService;
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
public class MessageServiceImpl implements IMessageService {
    private final IMessageRepository messageRepository;
    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void remove(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (!message.isPresent()) {
            return;
        }
        messageRepository.deleteById(id);
    }

}

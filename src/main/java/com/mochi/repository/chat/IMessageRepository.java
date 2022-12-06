package com.mochi.repository.chat;

import com.mochi.model.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMessageRepository extends JpaRepository<Message, Long> {
}

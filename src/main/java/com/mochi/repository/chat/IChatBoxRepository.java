package com.mochi.repository.chat;

import com.mochi.model.chat.ChatBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChatBoxRepository extends JpaRepository<ChatBox, Long> {
}

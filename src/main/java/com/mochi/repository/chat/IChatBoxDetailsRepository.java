package com.mochi.repository.chat;

import com.mochi.model.chat.ChatBox;
import com.mochi.model.chat.ChatBoxDetails;
import com.mochi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IChatBoxDetailsRepository extends JpaRepository<ChatBoxDetails, Long> {
    List<ChatBoxDetails> findByChatBox(ChatBox chatBox);
    List<ChatBoxDetails> findByUser(User user);
    Optional<ChatBoxDetails> findByChatBoxAndUser(ChatBox chatBox, User user);
}

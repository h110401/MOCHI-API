package com.mochi.repository.chat;

import com.mochi.model.chat.ChatBoxDetails;
import com.mochi.model.chat.ChatBoxDetailsMessages;
import com.mochi.model.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChatBoxDetailsMessagesRepository extends JpaRepository<ChatBoxDetailsMessages, Long> {
    void deleteByChatBoxDetailsAndMessage(ChatBoxDetails chatBoxDetails, Message message);
}

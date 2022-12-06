package com.mochi.service.chat;

import com.mochi.model.chat.ChatBox;
import com.mochi.model.chat.ChatBoxDetails;
import com.mochi.model.user.User;
import com.mochi.service.IGenericService;

import java.util.List;

public interface IChatBoxDetailsService extends IGenericService<ChatBoxDetails> {
    List<ChatBoxDetails> findByChatBox(ChatBox chatBox);

    List<ChatBoxDetails> findByUser(User user);
}

package com.mochi.service.chat;

import com.mochi.model.chat.ChatBoxStatus;
import com.mochi.model.chat.Message;
import com.mochi.model.user.User;

public interface IChatService {
    Message sendMessageToChatBox(User user, String message, Long idChatBox);

    void addUserToChatBox(Long idUser, Long idChatBox);

    void deleteMessageAtYourChatBoxDetails(Long idMessage, Long idChatBoxDetails);

    void retrieveMessage(Long idMessage);

    boolean setChatBoxStatus(Long idChatBox, ChatBoxStatus status);
}

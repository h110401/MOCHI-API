package com.mochi.dto.response;

import com.mochi.model.chat.*;
import com.mochi.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseChatBox {
    private Long id;
    private User user;
    private ChatBoxStatus status;
    private ChatBox chatBox;
    private List<Message> messages;
    private List<User> users;

}

package com.mochi.dto.response;

import com.mochi.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSearchUser {
    private User user;
    private FriendStatus status;
}

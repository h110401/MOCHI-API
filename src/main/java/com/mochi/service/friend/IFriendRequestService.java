package com.mochi.service.friend;

import com.mochi.model.friend.FriendRequest;
import com.mochi.model.user.User;
import com.mochi.service.IGenericService;

public interface IFriendRequestService extends IGenericService<FriendRequest> {
    FriendRequest findBySenderAndReceiver(User sender, User receiver);
    boolean acceptRequest(Long idSender, Long idReceiver);
}

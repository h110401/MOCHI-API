package com.mochi.repository.friend;

import com.mochi.model.friend.FriendRequest;
import com.mochi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    FriendRequest findBySenderAndReceiver(User sender, User receiver);

    void removeBySenderAndReceiver(User sender, User receiver);
}

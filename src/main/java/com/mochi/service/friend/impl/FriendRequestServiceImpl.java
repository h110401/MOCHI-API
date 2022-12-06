package com.mochi.service.friend.impl;

import com.mochi.model.friend.FriendRequest;
import com.mochi.model.user.User;
import com.mochi.repository.friend.IFriendRequestRepository;
import com.mochi.repository.user.IUserRepository;
import com.mochi.service.friend.IFriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements IFriendRequestService {

    private final IFriendRequestRepository friendRequestRepository;
    private final IUserRepository userRepository;

    @Override
    public List<FriendRequest> findAll() {
        return friendRequestRepository.findAll();
    }

    @Override
    public Page<FriendRequest> findAll(Pageable pageable) {
        return friendRequestRepository.findAll(pageable);
    }

    @Override
    public Optional<FriendRequest> findById(Long id) {
        return friendRequestRepository.findById(id);
    }

    @Override
    public FriendRequest save(FriendRequest friendRequest) {
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public void remove(Long id) {
        friendRequestRepository.deleteById(id);
    }

    @Override
    public FriendRequest findBySenderAndReceiver(User sender, User receiver) {
        return friendRequestRepository.findBySenderAndReceiver(sender, receiver);
    }

    @Override
    public boolean acceptRequest(Long idSender, Long idReceiver) {
        Optional<User> sender = userRepository.findById(idSender);
        Optional<User> receiver = userRepository.findById(idReceiver);
        if (!sender.isPresent() || !receiver.isPresent()) {
            return false;
        }
        friendRequestRepository.removeBySenderAndReceiver(sender.get(), receiver.get());
        sender.get().getProfile().getFriends().add(receiver.get());
        receiver.get().getProfile().getFriends().add(sender.get());
        return true;
    }
}

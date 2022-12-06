package com.mochi.API;

import com.mochi.dto.response.ResponseMessage;
import com.mochi.model.friend.FriendRequest;
import com.mochi.model.user.User;
import com.mochi.security.userprincipal.UserDetailsServiceIMPL;
import com.mochi.service.friend.IFriendRequestService;
import com.mochi.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/friends")
@RequiredArgsConstructor
@CrossOrigin
public class FriendAPI {

    private final IUserService userService;
    private final UserDetailsServiceIMPL userDetailsServiceIMPL;
    private final IFriendRequestService friendRequestService;

    @PostMapping("send/{id}")
    public ResponseEntity<?> sendFriendRequest(@PathVariable Long id) {
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        if (Objects.equals(currentUser.getId(), id)) {
            return new ResponseEntity<>(new ResponseMessage("request_invalid"), NOT_ACCEPTABLE);
        }
        Optional<User> sendTo = userService.findById(id);
        if (!sendTo.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("user_not_found"), NOT_FOUND);
        }
        FriendRequest bySenderAndReceiver = friendRequestService.findBySenderAndReceiver(currentUser, sendTo.get());
        FriendRequest bySenderAndReceiver1 = friendRequestService.findBySenderAndReceiver(sendTo.get(), currentUser);
        if (bySenderAndReceiver != null) {
            friendRequestService.remove(bySenderAndReceiver.getId());
            return new ResponseEntity<>(new ResponseMessage("request_retrieved"), OK);
        }
        if (bySenderAndReceiver1 != null) {
            friendRequestService.remove(bySenderAndReceiver1.getId());
            return new ResponseEntity<>(new ResponseMessage("request_declined"), OK);
        }
        friendRequestService.save(new FriendRequest(currentUser, sendTo.get()));
        return new ResponseEntity<>(new ResponseMessage("request_sent"), OK);
    }

    @PutMapping("accept/{id}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long id) {
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        boolean check = friendRequestService.acceptRequest(id, currentUser.getId());
        return new ResponseEntity<>(check ? OK : NOT_ACCEPTABLE);
    }

    @GetMapping("list/{username}")
    public ResponseEntity<?> friendList(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        Set<User> friends = user.getProfile().getFriends();
        return new ResponseEntity<>(friends, OK);
    }

}

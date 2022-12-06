package com.mochi.API;

import com.mochi.dto.response.ResponseMessage;
import com.mochi.dto.response.ResponseSearchUser;
import com.mochi.model.user.User;
import com.mochi.security.userprincipal.UserDetailsServiceIMPL;
import com.mochi.service.friend.IFriendRequestService;
import com.mochi.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mochi.dto.response.FriendStatus.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserAPI {
    private final IUserService userService;
    private final UserDetailsServiceIMPL userDetailsServiceIMPL;
    private final IFriendRequestService friendRequestService;

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Optional<User> user) {
        if (!user.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("user-not-found"), NOT_FOUND);
        }
        return ResponseEntity.ok(user.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("user-not-found"), NOT_FOUND);
        }
        userService.remove(id);
        return ResponseEntity.ok(new ResponseMessage("delete-success"));
    }

    @GetMapping("search/{search}")
    public ResponseEntity<?> searchUser(@PathVariable String search) {
        List<User> users = userService.search(search);
        if (users.size() == 0) {
            return new ResponseEntity<>(new ResponseMessage("not_found"), NO_CONTENT);
        }
        List<ResponseSearchUser> result = new ArrayList<>();
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        for (User user : users) {
            if (friendRequestService.findBySenderAndReceiver(currentUser, user) != null) {
                result.add(new ResponseSearchUser(user, SENT));
            } else if (friendRequestService.findBySenderAndReceiver(user, currentUser) != null) {
                result.add(new ResponseSearchUser(user, WAITING));
            } else if (currentUser.getProfile().getFriends().contains(user)) {
                result.add(new ResponseSearchUser(user, FRIEND));
            } else {
                result.add(new ResponseSearchUser(user, NOT_FRIEND));
            }
        }
        return ResponseEntity.ok(result);
    }
}

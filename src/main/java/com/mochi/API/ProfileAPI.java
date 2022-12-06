package com.mochi.API;

import com.mochi.dto.response.ResponseMessage;
import com.mochi.model.user.ChangeAvatar;
import com.mochi.model.user.User;
import com.mochi.security.userprincipal.UserDetailsServiceIMPL;
import com.mochi.service.chat.IChatBoxDetailsService;
import com.mochi.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/profile")
@CrossOrigin
@RequiredArgsConstructor
public class ProfileAPI {
    private final UserDetailsServiceIMPL userDetailsServiceIMPL;
    private final IUserService userService;

    @PutMapping("avatar")
    public ResponseEntity<?> changeAvatar(@RequestBody ChangeAvatar changeAvatar) {
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        currentUser.setAvatar(changeAvatar.getAvatar());
        userService.save(currentUser);
        return ResponseEntity.ok(new ResponseMessage("avatar-changed"));
    }

    @GetMapping("{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(new ResponseMessage("username_not_found"), NOT_FOUND);
        }
        return new ResponseEntity<>(user, OK);
    }


}

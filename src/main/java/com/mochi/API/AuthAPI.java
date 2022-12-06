package com.mochi.API;

import com.mochi.dto.request.LoginDTO;
import com.mochi.dto.request.SignUpDTO;
import com.mochi.dto.response.JwtResponse;
import com.mochi.dto.response.ResponseMessage;
import com.mochi.model.user.User;
import com.mochi.model.user.UserFactory;
import com.mochi.security.jwt.JwtProvider;
import com.mochi.security.userprincipal.UserPrincipal;
import com.mochi.service.user.IRoleService;
import com.mochi.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.mochi.model.user.RoleName.*;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthAPI {
    private final IRoleService roleService;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserFactory userFactory;


    @PostMapping
    public ResponseEntity<JwtResponse> auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(
                principal.getId(),
                jwtProvider.createToken(authentication),
                principal.getUsername(),
                principal.getName(),
                principal.getAvatar(),
                principal.getAuthorities()
        ));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody
            LoginDTO loginDTO
    ) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(authToken);
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(
                    principal.getId(),
                    jwtProvider.createToken(authentication),
                    principal.getUsername(),
                    principal.getName(),
                    principal.getAvatar(),
                    principal.getAuthorities()
            ));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("unauthorized"), UNAUTHORIZED);
        }
    }

    @PostMapping("signup")
    public ResponseEntity<ResponseMessage> signup(
            @Valid
            @RequestBody
            SignUpDTO signUpDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasFieldErrors() || !signUpDTO.getPassword().equals(signUpDTO.getRepeat())) {
            return new ResponseEntity<>(new ResponseMessage("invalid-field"), NOT_ACCEPTABLE);
        }
        if (userService.existsByUsername(signUpDTO.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("username-existed"), NOT_ACCEPTABLE);
        }
        if (userService.existsByEmail(signUpDTO.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("email-existed"), NOT_ACCEPTABLE);
        }
        signUpDTO.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        User user = userFactory.build(signUpDTO, roleService.findByName(ROLE_USER));
        userService.save(user);
        return ResponseEntity.ok(new ResponseMessage("signup-success"));
    }
}

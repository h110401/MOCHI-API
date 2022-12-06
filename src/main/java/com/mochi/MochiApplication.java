package com.mochi;

import com.mochi.model.chat.ChatBox;
import com.mochi.model.user.Profile;
import com.mochi.model.user.Role;
import com.mochi.model.user.RoleName;
import com.mochi.model.user.User;
import com.mochi.service.chat.IChatBoxDetailsService;
import com.mochi.service.chat.IChatBoxService;
import com.mochi.service.chat.IChatService;
import com.mochi.service.chat.IMessageService;
import com.mochi.service.user.IProfileService;
import com.mochi.service.user.IRoleService;
import com.mochi.service.user.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.mochi.model.user.RoleName.*;

@SpringBootApplication
public class MochiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MochiApplication.class, args);
    }

    @Bean
    CommandLineRunner run(@Value("${default.avatar}") String avatar,
                          IUserService userService,
                          IRoleService roleService,
                          PasswordEncoder passwordEncoder,
                          IMessageService messageService,
                          IChatBoxService chatBoxService,
                          IChatBoxDetailsService chatBoxDetailsService,
                          IProfileService profileService,
                          IChatService chatService) {
        return args -> {
            roleService.save(new Role(null, ROLE_ADMIN));
            roleService.save(new Role(null, ROLE_PM));
            roleService.save(new Role(null, ROLE_USER));

            User admin = userService.save(new User("", "Admin", "admin", "admin@gmail.com", passwordEncoder.encode("1234"), avatar));
            User manager = userService.save(new User("", "Manager", "manager", "manager@gmail.com", passwordEncoder.encode("1234"), avatar));
            User user = userService.save(new User("", "User", "user", "user@gmail.com", passwordEncoder.encode("1234"), avatar));

            profileService.save(new Profile(admin));
            profileService.save(new Profile(manager));
            profileService.save(new Profile(user));

            roleService.addRoleToUser("admin", ROLE_ADMIN);
            roleService.addRoleToUser("admin", ROLE_PM);
            roleService.addRoleToUser("admin", ROLE_USER);

            roleService.addRoleToUser("manager", ROLE_PM);
            roleService.addRoleToUser("manager", ROLE_USER);

            roleService.addRoleToUser("user", ROLE_USER);

            ChatBox save1 = chatBoxService.save(new ChatBox(null, "test1", admin));
            chatService.addUserToChatBox(1L, 1L);
            chatService.addUserToChatBox(2L, 1L);
            chatService.addUserToChatBox(3L, 1L);

            ChatBox save2 = chatBoxService.save(new ChatBox(null, "test2", admin));
            chatService.addUserToChatBox(1L, 2L);
            chatService.addUserToChatBox(2L, 2L);

            chatService.sendMessageToChatBox(admin, "Hello I'm Admin", 1L);
            chatService.sendMessageToChatBox(admin, "Hello I'm Admin", 1L);
            chatService.sendMessageToChatBox(admin, "Hello I'm Admin", 1L);

            chatService.sendMessageToChatBox(manager, "Hello I'm Manager", 1L);
            chatService.sendMessageToChatBox(manager, "Hello I'm Manager", 1L);

            chatService.sendMessageToChatBox(user, "Hello I'm User", 1L);

            chatService.sendMessageToChatBox(admin, "Hello I'm Admin part2", 2L);
            chatService.sendMessageToChatBox(admin, "Hello I'm Admin part2", 2L);

            chatService.sendMessageToChatBox(manager, "Hello", 2L);

        };
    }
}

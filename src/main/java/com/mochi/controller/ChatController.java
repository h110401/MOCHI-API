package com.mochi.controller;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.mochi.dto.response.ResponseChatBox;
import com.mochi.dto.response.ResponseMessage;
import com.mochi.model.chat.*;
import com.mochi.model.user.User;
import com.mochi.security.userprincipal.UserDetailsServiceIMPL;
import com.mochi.service.chat.IChatBoxDetailsService;
import com.mochi.service.chat.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mochi.model.chat.ChatBoxStatus.*;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("ws")
@RequiredArgsConstructor
@CrossOrigin
public class ChatController {
    private final SocketIOServer server;
    private final UserDetailsServiceIMPL userDetailsServiceIMPL;
    private final IChatBoxDetailsService chatBoxDetailsService;
    private final IChatService chatService;

    @GetMapping("connect")
    public ResponseEntity<?> connect() {
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        server.addNamespace("/chat/" + currentUser.getUsername());
        List<ChatBoxDetails> details = chatBoxDetailsService.findByUser(currentUser);
        boolean have_unread_message = false;
        for (ChatBoxDetails detail : details) {
            if (detail.getStatus() == UNREAD) {
                have_unread_message = true;
                break;
            }
        }
        return new ResponseEntity<>(have_unread_message, OK);
    }

    @GetMapping("chat_boxes")
    public ResponseEntity<?> chatBoxes() {
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        List<ChatBoxDetails> details = chatBoxDetailsService.findByUser(currentUser);
        return ResponseEntity.ok(build(details));
    }

    public List<ResponseChatBox> build(List<ChatBoxDetails> details) {
        List<ResponseChatBox> responses = new ArrayList<>();
        for (ChatBoxDetails detail : details) {
            List<ChatBoxDetails> byChatBox = chatBoxDetailsService.findByChatBox(detail.getChatBox());
            List<User> users = byChatBox.stream().map(ChatBoxDetails::getUser).collect(Collectors.toList());
            List<Message> collect = detail.getChatBoxDetailsMessages().stream().map(ChatBoxDetailsMessages::getMessage).collect(Collectors.toList());
            responses.add(new ResponseChatBox(detail.getId(), detail.getUser(), detail.getStatus(), detail.getChatBox(), collect, users));
        }
        return responses;
    }

    @PostMapping("send_message")
    public ResponseEntity<?> sendMessage(@RequestBody ChatObject chatObject) {
        User currentUser = userDetailsServiceIMPL.getCurrentUser();
        Message message = chatService.sendMessageToChatBox(currentUser, chatObject.getMessage(), chatObject.getChatBox().getId());
        List<ChatBoxDetails> details = chatBoxDetailsService.findByChatBox(chatObject.getChatBox());
        List<User> users = details.stream().map(ChatBoxDetails::getUser).collect(Collectors.toList());
        for (User user : users) {
            SocketIONamespace namespace = server.getNamespace("/chat/" + user.getUsername());
            if (namespace != null) {
                namespace.getBroadcastOperations().sendEvent("receive_message", new ResponseChatObject(message, chatObject.getChatBox()));
            }
        }
        return new ResponseEntity<>(OK);
    }

    @PutMapping("read/{id}")
    public ResponseEntity<?> setChatBoxStatusToRead(@PathVariable Long id) {
        boolean check = chatService.setChatBoxStatus(id, READ);
        return check ? new ResponseEntity<>(OK) : new ResponseEntity<>(new ResponseMessage("chat_box_details_not_found"), NOT_ACCEPTABLE);
    }
}

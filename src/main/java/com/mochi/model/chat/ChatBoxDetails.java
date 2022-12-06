package com.mochi.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mochi.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

import static com.mochi.model.chat.ChatBoxStatus.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "chat_box_details", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "chat_box_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatBoxDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Enumerated(STRING)
    private ChatBoxStatus status;
    @ManyToOne
    private ChatBox chatBox;

    @OneToMany
            (cascade = ALL, mappedBy = "chatBoxDetails", orphanRemoval = true)
    private Set<ChatBoxDetailsMessages> chatBoxDetailsMessages;


    public ChatBoxDetails(User user, ChatBox chatBox) {
        this.user = user;
        this.chatBox = chatBox;
        this.status = UNREAD;
    }
}

package com.mochi.model.chat;

import com.mochi.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "chat_box")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatBox {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private User user;

    public ChatBox(Long id) {
        this.id = id;
    }
}

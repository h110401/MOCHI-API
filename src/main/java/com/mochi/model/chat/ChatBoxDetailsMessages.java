package com.mochi.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "chat_box_details_messages",
        uniqueConstraints = @UniqueConstraint(columnNames = {"chat_box_details_id", "message_id"})
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatBoxDetailsMessages {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "chat_box_details_id", referencedColumnName = "id")
    @JsonIgnore
    private ChatBoxDetails chatBoxDetails;
    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private Message message;

    public ChatBoxDetailsMessages(ChatBoxDetails chatBoxDetails, Message message) {
        this.chatBoxDetails = chatBoxDetails;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChatBoxDetailsMessages that = (ChatBoxDetailsMessages) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

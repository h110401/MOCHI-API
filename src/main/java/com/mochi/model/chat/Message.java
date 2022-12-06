package com.mochi.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mochi.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.*;
import static javax.persistence.TemporalType.*;
import static org.hibernate.annotations.GenerationTime.*;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Lob
    @NotBlank
    private String content;

    @Generated(INSERT)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false)
    private Date created;
    @ManyToOne
    private User user;


    @OneToMany(cascade = ALL, mappedBy = "message", orphanRemoval = true)
    @JsonIgnore
    private Set<ChatBoxDetailsMessages> chatBoxDetailsMessages;

    public Message(String content, User user) {
        this.content = content;
        this.user = user;
    }

}

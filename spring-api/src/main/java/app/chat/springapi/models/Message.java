package app.chat.springapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
// todo this is stored in the database, it is an @entity

@Entity
@Table(name = "messages", schema = "public")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "chat_id")
    private Long chatId;

    @Basic
    @Column(name = "created_at")
    private Instant createdAt;

    @Basic
    @Column(name = "created_by_user_id")
    private String createdByUserId;

}

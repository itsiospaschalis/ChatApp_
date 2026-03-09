package app.chat.springapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity

@Table(name="chats")
public class Chats{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id",nullable=false)
    private Long id;

    @Column(name="user_id",nullable=false)
    private Long user_id;

    @Column(name="title",length = Integer.MAX_VALUE)
    private String title;

    @Column(name="created_at")
    private Instant created_at;

    @Column(name="username")
    private String username;
}

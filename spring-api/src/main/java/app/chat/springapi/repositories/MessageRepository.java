package app.chat.springapi.repositories;

import app.chat.springapi.models.Message;
import app.chat.springapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import app.chat.springapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

// afoy extends JpaRepository kathe instance exei idi mesa ta basic CRUD methods opws save, findById, findAll, deleteById
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM messages WHERE chat_id = :chatId ")
    public List<Message> findMessagesByChat(@Param("chatId") Long chatId);

}

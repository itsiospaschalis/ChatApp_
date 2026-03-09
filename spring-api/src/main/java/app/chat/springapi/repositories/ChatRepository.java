package app.chat.springapi.repositories;

import app.chat.springapi.models.Chats;
import app.chat.springapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
@Repository
public interface ChatRepository extends JpaRepository<Chats,Long> {
    //--------------------------------------------------------------------------

    @Query(nativeQuery=true,
        value= "SELECT c.*" +
                " FROM public.chats c inner join public.users u on c.user_id=u.id"+
                " WHERE (:userId is null or user_id = :userId) AND " +
                "(:username is null or u.username=:username) AND " +
                "(CAST(:from AS timestamp) is null or created_at<=:from) AND" +
                "(CAST(:to AS timestamp) is null or created_at <:to)" )


    List<Chats> findAll(Long userId,
                        String username,
                        Instant from,
                        Instant to);
//--------------------------------------------------------------------------

    @Query(nativeQuery = true,
            value="SELECT * FROM chats WHERE username = :usern ")

    public Chats findChatByUsername(@Param("usern") String username);
//--------------------------------------------------------------------------
    @Query(nativeQuery = true,
            value="SELECT * FROM chats WHERE id = :chatn ")

    public Chats findChatByChatId(@Param("chatn") Long id);

}

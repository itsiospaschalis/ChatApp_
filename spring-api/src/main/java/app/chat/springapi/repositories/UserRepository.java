package app.chat.springapi.repositories;

import app.chat.springapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true,
            value="SELECT * FROM users WHERE username = :usern ")

    public User findUserByUsername(@Param("usern") String username);

//
//    @Query(nativeQuery = true,)
//            value="SELECT * FROM users WHERE email = :emailn ")
//
//    public User editUserByUsername(@Param("usern") String username);

}



//an theloyme 2 criteria
//public interface UserRepository extends JpaRepository<User, Long> {
//    @Query(nativeQuery = true,
//            value="SELECT * FROM users WHERE username = : usern or secondusername= : second")
//    public User findUserByUsername(@Param("usern") String username,@Param("second") String secondusername);
//}

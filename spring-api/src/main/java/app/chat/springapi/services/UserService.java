package app.chat.springapi.services;

import app.chat.springapi.exceptions.BootcampException;
import app.chat.springapi.models.User;
import app.chat.springapi.models.dtos.JwtDTO;
import app.chat.springapi.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

//Logger gia na kanoume log ta messages
    Logger logger = LoggerFactory.getLogger(UserService.class);


    //-----------(Constructor) userRepository INJECTION----------------
    private UserRepository userRepository;
    private JwtEncoder jwtEncoder;

    @Autowired
    public UserService(UserRepository userRepository,JwtEncoder jwtEncoder)
    {
        this.userRepository = userRepository;
//        logger.debug("UserService initialized with UserRepository. Reference to:" + this);
        this.jwtEncoder=jwtEncoder;

    }
//-----------------------METHODS-----------------------
    // sta Services Methods, kratame to onoma GET*** Sta Repositories to onoma FIND***
    // ---Service Method to get all users

public List<User> getAllUsers(){
    logger.debug("aaaaaaaaaaaaaaUserService initialized with UserRepository. Reference to :"+this);

    return this.userRepository.findAll();
}

//---Service Method to get user by username

public User getUserByUsername(@PathVariable String username){
        return userRepository.findUserByUsername(username);
}


//-----------------Service Method to create a new user------------------
    public User createUser(User user_) throws BootcampException {

    //Validate that the new user, does not have the same username with an existing user
        User existingUser= userRepository.findUserByUsername(user_.getUsername());
        if (existingUser != null){
            throw new BootcampException(HttpStatus.BAD_REQUEST, "User with username '"+user_.getUsername()+
                    "' already exists");
        }
        //  an erthei edw tote shmainei oti einai entaksei kainourios xrhsths

        //btw we save the user to the database. this is not the scope of the createUser method
        //but we do it here just in case
        user_=userRepository.save(user_);
        return user_;


    }

    public User updateUser(String username,User updatedUser) throws BootcampException {

    User existingUser= userRepository.findUserByUsername(username);

// TODO'S    existingUser.setUsername(updatedUser.getUsername());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setName(updatedUser.getName());
    existingUser.setPassword(updatedUser.getPassword());

    userRepository.save(existingUser);
    return existingUser;}



// we want to return a UserDetails object.
// To achieve that we can make our User entity implement the UserDetails interface

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=getUserByUsername(username);
        return user;
    }

    //business logic to get the logged in user
    public User getLoggedInUser(Authentication authentication) throws BootcampException {
        User loggedInUser;

        if (authentication.getPrincipal() instanceof User) {
            loggedInUser = (User) authentication.getPrincipal();
        } else if (authentication.getPrincipal() instanceof Jwt) {
            loggedInUser = this.getUserByUsername(((Jwt) authentication.getPrincipal()).getSubject());
        } else {
            throw new BootcampException(HttpStatus.UNAUTHORIZED, "You are not authenticated.");
        }

        return loggedInUser;
    }

    public JwtDTO generateJwtForAuthUser(Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();

        Instant now = Instant.now();


        JwtClaimsSet claimsSet= JwtClaimsSet.builder()
                .issuer("localhost:8080")
                .subject(loggedInUser.getUsername())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("role", loggedInUser.getRole())
                .claim("email", loggedInUser.getEmail())
                .claim("name", loggedInUser.getName())
                .claim("id", loggedInUser.getId())
                .build();

        Jwt jwt=jwtEncoder.encode(JwtEncoderParameters.from(claimsSet));

        JwtDTO jwtDTO=new JwtDTO();
        jwtDTO.setToken(jwt.getTokenValue());
        return jwtDTO;
    }
}


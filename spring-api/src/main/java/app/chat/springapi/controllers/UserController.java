package app.chat.springapi.controllers;

import app.chat.springapi.exceptions.BootcampException;
import app.chat.springapi.models.dtos.JwtDTO;

import app.chat.springapi.models.User;
import app.chat.springapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//======== ftiaxame sthn bash ta dedomena=======
// ==========kai den xreiazomaste FakeData===========
@RestController
public class UserController {


//    // ftiaxnoyme mia lista me tous users tou typoy List<User>
//    // o <User> einai ena entity poy mpainei sto model
//    private List<User> users = new ArrayList<>();
//
//    // ftiaxnoyme  constructor me dedomena mesa
//    public UserController() {
//        FakeUsers fakeUsers = new FakeUsers();
//        users=fakeUsers.createUsers();
//

    // -----------DEPENDENCY INJECTION----------------
    private UserService userService;
    private JwtEncoder jwtEncoder;

    @Autowired
    public UserController(UserService userService,
                          JwtEncoder jwtEncoder) {
        this.userService = userService;
        this.jwtEncoder = jwtEncoder;

    }


    //We create an empty constructor
    public UserController() {
    }

    //------------------METHODS--------------------
//Controller to get all users
    @GetMapping("/users")
    public List<User> getAllUsers(Authentication authentication) throws BootcampException {

        User loggedInUser = userService.getLoggedInUser(authentication);

        //This is required to do the validation of the role. Its not business logic, so its here in the controller
        if (!loggedInUser.getRole().equals("ADMINISTRATOR_USER")) {
            throw new BootcampException(HttpStatus.FORBIDDEN, "Regular users cannot access all susers");
        }

        return userService.getAllUsers();
    }

    //Controller to get user by username
    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username, Authentication authentication) throws BootcampException {
        User loggedInUser=userService.getLoggedInUser(authentication);
        if (!loggedInUser.getRole().equals("ADMINISTRATOR_USER") &&
                !loggedInUser.getUsername().equals(username)) {
            throw new BootcampException(HttpStatus.FORBIDDEN, "Regular users cannot access all users");
        }


        return userService.getUserByUsername(username);
    }

    //Controller Method to create a new user
    @PostMapping("/users")
    public User createUser(@RequestBody User user_) throws Exception {
        // The business logic to create a new user is moved to the UserService class

        return userService.createUser(user_);
    }


    @GetMapping("/login")
    public JwtDTO login(Authentication authentication) {
        JwtDTO jwtDTO = userService.generateJwtForAuthUser(authentication);
        return jwtDTO;
    }


    // first parameter is the username from the path,the second is the updated user from the request body
    // the updateuser method updates the USER fields with the elements of the input of the request body
    @PutMapping("/users/{username}")
    public User editUserByUsername(@PathVariable String username, @RequestBody User updatedUser,Authentication authentication) throws BootcampException {
        User loggedInUser = userService.getLoggedInUser(authentication);
        if (!loggedInUser.getUsername().equals(username)) {
            throw new BootcampException(HttpStatus.FORBIDDEN, "Regular users cannot edit profiles of other users");
        }
        return userService.updateUser(username,updatedUser);
    }


}

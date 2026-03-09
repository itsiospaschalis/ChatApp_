package app.chat.springapi.FakeData_REDUNDANT;

import app.chat.springapi.models.User;

import java.util.ArrayList;
import java.util.List;

public class FakeUsers {

    public List<User> users = new ArrayList<>();

    public List<User> createUsers() {

        // Create some fake users from the User model
        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setPassword("1234");
        user1.setEmail("john_doe@gmail.com");
        user1.setName("John Doe");
        user1.setId(1L); // Set a unique ID for the user

        User user2 = new User();
        user2.setUsername("jane_doe");
        user2.setPassword("5678");
        user2.setEmail("jane_doe@gmail.com");
        user2.setName("Jane Doe");
        user2.setId(2L);
        // Set a unique ID for the user
        users.add(user1);
        users.add(user2);

        return users;
    }
}

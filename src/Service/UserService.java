package Service;

import Model.User;

import java.util.HashMap;
import java.util.Map;
/**
 * Service class for managing user registrations.
 */

public class UserService {
    /**
     * A map to store users by their username.
     */
    private Map<String, User> users = new HashMap<>();
    /**
     * Registers a new user with the specified username, password, and admin status.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param isAdmin  whether the new user has admin privileges
     * @return true if the user was successfully registered, false if the username is already taken
     */

    public boolean registerUser(String username, String password, boolean isAdmin) {
        if(users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password, isAdmin));
        return true;
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
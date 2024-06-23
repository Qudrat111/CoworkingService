package Utility;

import Model.User;
import Service.UserService;

public class Authenticator {
    private UserService userService;

    public Authenticator(UserService userService) {
        this.userService = userService;
    }

    public User login(String username, String password) {
        return userService.authenticate(username, password);
    }
}
package resources;

import Model.ApiResponse;
import Model.User;
import Service.UserService;

public class UserResource {
    private UserService userService;

    public ApiResponse register(User user) {
        boolean b = userService.registerUser(user.getUsername(), user.getPassword(), user.isAdmin());
        if (b) {
            return new ApiResponse("Registered successfully",200,b);
        }
        return new ApiResponse("Invalid username/password",400,false);
    }

    public ApiResponse login(String username, String password) {
        User authenticate = userService.authenticate(username, password);
        if (authenticate == null) {
            return new ApiResponse("Invalid username/password",400,authenticate);
        }
        return new ApiResponse("Login successful",200,authenticate);
    }

}

package user_management.service;

import user_management.model.User;

public interface UserService {
    User authenticate(String username, String password);
    boolean registerUser(User user);
    User getUserInfo(String username);
    boolean updateUser(User user);
    boolean deleteUser(String username);

    User getUserByUsername(String username);

    boolean authenticateUser(String username, String password);
}

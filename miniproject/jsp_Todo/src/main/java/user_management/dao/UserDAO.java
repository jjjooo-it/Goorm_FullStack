package user_management.dao;

import user_management.model.User;

public interface UserDAO {
    User findByUsername(String username);
    boolean save(User user);
    boolean update(User user);
    boolean delete(String username);

    boolean registerUser(User user);

    User getUserByUsername(String username);

    boolean authenticateUser(String username, String password);
}

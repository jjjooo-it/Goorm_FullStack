package user_management.service;

import user_management.dao.UserDAO;
import user_management.dao.UserDAOImpl;
import user_management.model.User;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public User authenticate(String username, String password) {
        return null;
    }

    @Override
    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }

    @Override
    public User getUserInfo(String username) {
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return userDAO.authenticateUser(username, password);
    }
}
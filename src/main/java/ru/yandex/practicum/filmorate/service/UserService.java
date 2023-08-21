package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDAO;

import java.util.*;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    @Autowired
    public UserService(@Qualifier("userDaoImpl") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public Optional<User> getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    public User addUserWithExistingId(User user) {
        return userDAO.addUserWithExistingId(user);
    }

    public User addUserWithNoId(User user) {
        return userDAO.addUserWithNoId(user);
    }

    public User addUserWithNoName(User user) {
        return userDAO.addUserWithNoName(user);
    }

    public User addUser(User user) {
        return userDAO.addUser(user);
    }

    public User updateUserById(User user) {
        return userDAO.updateUserById(user);
    }

    public Long deleteUserById(Long id) {
        return userDAO.deleteUserById(id);
    }

    public List<Long> addFriend(Long userId, Long friendId) {
        return userDAO.addFriends(userId, friendId);
    }

    public Long deleteFriend(Long userId, Long friendId) {
        return userDAO.deleteFriend(userId, friendId);
    }

    public List<User> getUsersFriends(Long userId) {
        return userDAO.getUsersFriends(userId);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        return userDAO.getCommonFriends(userId, friendId);
    }

}

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
        /*List<Long> friendsIds = new ArrayList<>();

        if (userId == null || friendId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<User> user = userDAO.getUserById(userId);
        Optional<User> friend = userDAO.getUserById(friendId);

        if (user.get().getFriends().contains(friend.get().getId())) {
            throw new ValidationException("A friend has already been added to user's friends list.");
        }
        if (user.get().getId() == friend.get().getId()) {
            throw new ValidationException("User cannot be added to its own friend list.");
        }

        user.get().getFriends().add(friend.get().getId());
        friend.get().getFriends().add(user.get().getId());

        user.get().setIsFriendStatus(true);
        friend.get().setIsFriendStatus(true);


        log.info("User '{}' is now friends with user '{}'.", user.get().getLogin(), user.get().getLogin());

        friendsIds = List.of(user.get().getId(), friend.get().getId());

        return friendsIds;*/
    }

    public Long deleteFriend(Long userId, Long friendId) {
        return userDAO.deleteFriend(userId, friendId);
        /*if (userId == null || friendId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<User> user = userDAO.getUserById(userId);
        Optional<User> friend = userDAO.getUserById(friendId);

        if (user.get().getFriends() == null || friend.get().getFriends() == null) {
            user.get().setFriends(new HashSet<>());
            friend.get().setFriends(new HashSet<>());
        }

        if (user.isPresent() && friend.isPresent()) {
            if (user.get().getFriends().contains(friend.get().getId())) {
                user.get().getFriends().remove(friend.get().getId());
                friend.get().getFriends().remove(user.get().getId());
            } else {
                return new String("User doesn't have a friend with the ID of - " + friend.get().getId());
            }
        }
        return new String("User - " + friend.get().getName() + " has been deleted from friends.");*/
    }

    public List<User> getUsersFriends(Long userId) {
        return userDAO.getUsersFriends(userId);
        /*Optional<User> user = userDAO.getUserById(userId);

        if (user.get().getFriends() == null) {
            user.get().setFriends(new HashSet<>());
        }

        List<Long> usersFriendsIds = user.get().getFriends().stream()
                .sequential()
                .collect(Collectors.toList());

        List<User> userFriendsList = new ArrayList<>();

        for (int i = 0; i < usersFriendsIds.size(); i++) {
            Optional<User> friend = userDAO.getUserById(usersFriendsIds.get(i));
            userFriendsList.add(friend.get());
        }
        return userFriendsList;*/
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        return userDAO.getCommonFriends(userId, friendId);
        /*Optional<User> user = userDAO.getUserById(userId);
        Optional<User> friend = userDAO.getUserById(friendId);

        if (user.get().getFriends() == null || friend.get().getFriends() == null) {
            user.get().setFriends(new HashSet<>());
            friend.get().setFriends(new HashSet<>());
        }

        List<Long> commonFriendsIds = user.get().getFriends().stream()
                .filter(friendsFriends -> friend.get().getFriends().stream()
                        .anyMatch(usersFriends -> usersFriends.equals(friendsFriends))
                )
                .collect(Collectors.toList());

        List<User> commonFriends = new ArrayList<>();

        for (int i = 0; i < commonFriendsIds.size(); i++) {
            Optional<User> commonFriend = userDAO.getUserById(commonFriendsIds.get(i));
            commonFriends.add(commonFriend.get());
        }
        return commonFriends;*/
    }




}

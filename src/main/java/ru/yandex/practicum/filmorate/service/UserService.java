package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.controllers.UserValidationController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final Logger log = LoggerFactory.getLogger(UserValidationController.class);

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    /*public List<Long> addFriend(User user, User newFriend) {
        if (user == null || newFriend == null) {
            throw new ValidationException("One of the arguments is missing.");
        }
        if (user.getFriends().contains(newFriend.getId())) {
            throw new ValidationException("A friend has already been added to user's friends list.");
        }
        if (user.getId() == newFriend.getId()) {
            throw new ValidationException("User cannot be added to its own friends.");
        }
        user.getFriends().add(newFriend.getId());
        newFriend.getFriends().add(user.getId());

        List<Long> friendsIds = List.of(user.getId(), newFriend.getId());
        return friendsIds;
    }*/

    public List<Long> addFriend(Long userId, Long newFriendsId) {
        List<Long> friendsIds = new ArrayList<>();

        if (userId == null || newFriendsId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<User> user = userStorage.getUserById(userId, log);
        Optional<User> friend = userStorage.getUserById(newFriendsId, log);

        user.get().setFriends(new HashSet<>());
        friend.get().setFriends(new HashSet<>());

        if (user.isPresent() && friend.isPresent()) {
            if (user.get().getFriends() != null && user.get().getFriends().contains(friend.get().getId())) {
                throw new ValidationException("A friend has already been added to user's friends list.");
            }
            if (user.get().getId() == friend.get().getId()) {
                throw new ValidationException("User cannot be added to its own friend list.");
            } else {
                user.get().getFriends().add(friend.get().getId());
                friend.get().getFriends().add(user.get().getId());
                friendsIds = List.of(user.get().getId(), friend.get().getId());
            }
        }
        return friendsIds;
    }

    /*public void deleteFriend(User user, User friend) {
        if (user == null || friend == null) {
            throw new ValidationException("One of the arguments is missing.");
        }
        if (user.getFriends().contains(friend.getId())) {
            user.getFriends().remove(friend.getId());
            friend.getFriends().remove(user.getId());
        } else {
            throw new ValidationException("User does't have a friend with the ID of - " + friend.getId());
        }
    }*/

    public String deleteFriend(Long userId, Long friendId) {
        if (userId == null || friendId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<User> user = userStorage.getUserById(userId, log);
        Optional<User> friend = userStorage.getUserById(friendId, log);

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
        return new String("User - " + friend.get().getName() + " has been deleted from friends.");
    }

    public List<User> getUsersFriends(Long userId) {
        Optional<User> user = userStorage.getUserById(userId, log);

        if (user.get().getFriends() == null) {
            user.get().setFriends(new HashSet<>());
        }

        List<Long> usersFriendsIds = user.get().getFriends().stream()
                .sequential()
                .collect(Collectors.toList());

        List<User> userFriendsList = new ArrayList<>();

        for (int i = 0; i < usersFriendsIds.size(); i++) {
            Optional<User> friend = userStorage.getUserById(usersFriendsIds.get(i), log);
            userFriendsList.add(friend.get());
        }
        return userFriendsList;
    }

    /*public List<Long> getCommonFriendsList(User user1, User user2) {
        List<Long> commonFriends = user1.getFriends().stream()
                .filter(secondSet -> user2.getFriends().stream()
                        .anyMatch(firstSet -> firstSet.equals(secondSet))
                )
                .collect(Collectors.toList());
        return commonFriends;
    }*/

    public List<User> getCommonFriends(Long userId, Long friendId) {
        Optional<User> user = userStorage.getUserById(userId, log);
        Optional<User> friend = userStorage.getUserById(friendId, log);

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
            Optional<User> commonFriend = userStorage.getUserById(commonFriendsIds.get(i), log);
            commonFriends.add(commonFriend.get());
        }
        return commonFriends;
    }
}

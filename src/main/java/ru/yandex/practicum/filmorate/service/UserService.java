package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDaoImpl") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public Optional<User> getUserById(Long id) {
        return userStorage.getUserById(id);
    }

    public User addUserWithExistingId(User user) {
        return userStorage.addUserWithExistingId(user);
    }

    public User addUserWithNoId(User user) {
        return userStorage.addUserWithNoId(user);
    }

    public User addUserWithNoName(User user) {
        return userStorage.addUserWithNoName(user);
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUserById(User user) {
        return userStorage.updateUserById(user);
    }

    public Long deleteUserById(Long id) {
        return userStorage.deleteUserById(id);
    }

    public List<Long> addFriend(Long userId, Long newFriendsId) {
        List<Long> friendsIds = new ArrayList<>();

        if (userId == null || newFriendsId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<User> user = userStorage.getUserById(userId);
        Optional<User> friend = userStorage.getUserById(newFriendsId);

        if (user.isPresent() && friend.isPresent()) {
            if (user.get().getFriends() == null && friend.get().getFriends() == null) {
                user.get().setFriends(new HashSet<>());
                friend.get().setFriends(new HashSet<>());

                user.get().getFriends().add(friend.get().getId());
                friend.get().getFriends().add(user.get().getId());

                friendsIds = List.of(user.get().getId(), friend.get().getId());
            } else if (friend.get().getFriends() == null) {
                friend.get().setFriends(new HashSet<>());

                user.get().getFriends().add(friend.get().getId());
                friend.get().getFriends().add(user.get().getId());

                friendsIds = List.of(user.get().getId(), friend.get().getId());
            } else {
                user.get();
                if (user.get().getFriends().contains(friend.get().getId())) {
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
        }
        return friendsIds;
    }

    public String deleteFriend(Long userId, Long friendId) {
        if (userId == null || friendId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<User> user = userStorage.getUserById(userId);
        Optional<User> friend = userStorage.getUserById(friendId);

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
        Optional<User> user = userStorage.getUserById(userId);

        if (user.get().getFriends() == null) {
            user.get().setFriends(new HashSet<>());
        }

        List<Long> usersFriendsIds = user.get().getFriends().stream()
                .sequential()
                .collect(Collectors.toList());

        List<User> userFriendsList = new ArrayList<>();

        for (int i = 0; i < usersFriendsIds.size(); i++) {
            Optional<User> friend = userStorage.getUserById(usersFriendsIds.get(i));
            userFriendsList.add(friend.get());
        }
        return userFriendsList;
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        Optional<User> user = userStorage.getUserById(userId);
        Optional<User> friend = userStorage.getUserById(friendId);

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
            Optional<User> commonFriend = userStorage.getUserById(commonFriendsIds.get(i));
            commonFriends.add(commonFriend.get());
        }
        return commonFriends;
    }
}

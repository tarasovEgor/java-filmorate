package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User addUserWithExistingId(User user);

    User addUserWithNoId(User user);

    User addUserWithNoName(User user);

    User addUser(User user);

    User updateUserById(User user);

    Long deleteUserById(Long id);

    List<Long> addFriends(Long id, Long friendId);

    Long deleteFriend(Long userId, Long friendId);

    List<User> getUsersFriends(Long id);

    List<User> getCommonFriends(Long userId, Long friendId);
}

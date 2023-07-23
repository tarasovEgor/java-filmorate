package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    List<User> getAllUsers();

    Optional<User> getUserById(Long id, Logger log);

    User addUserWithExistingId(User user, Logger log);

    User addUserWithNoId(User user, Logger log);

    User addUserWithNoName(User user, Logger log);

    User addUser(User user, Logger log);

    User updateUserById(User user, Logger log);

    void deleteUserById(User user, Logger log);
}

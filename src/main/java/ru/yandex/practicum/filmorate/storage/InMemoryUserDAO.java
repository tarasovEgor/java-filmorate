package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Component
@Qualifier("inMemoryUserStorage")
public class InMemoryUserDAO implements UserDAO {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(users.values());
        return userList;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return users.values().stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }

    @Override
    public User addUserWithExistingId(User user) {
        user.setId(users.size() + 1L);
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User addUserWithNoId(User user) {
        user.setId(users.size() + 1L);
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        } else if (user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User addUserWithNoName(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        user.setId(users.size() + 1L);
        user.setName(user.getLogin());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User addUser(User user) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUserById(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("User does not exist.");
        }
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Long deleteUserById(Long id) {
        users.values().removeIf(x -> users.containsKey(id));
        return id;
    }

    @Override
    public List<Long> addFriends(Long id, Long friendId) {
        return null;
    }

    @Override
    public Long deleteFriend(Long userId, Long friendId) {
        return null;
    }

    @Override
    public List<User> getUsersFriends(Long id) {
        return null;
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long friendId) {
        return null;
    }
}

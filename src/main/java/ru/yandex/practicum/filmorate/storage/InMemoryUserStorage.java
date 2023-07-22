package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.errors.UserValidationErrors;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(users.values());
        return userList;
    }

    @Override
    public Optional<User> getUserById(Long id, Logger log) {
        return users.values().stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }

    @Override
    public User addUserWithExistingId(User user, Logger log) {
        user.setId(users.size() + 1L);
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_EMAIL_ERROR));
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_LOGIN_ERROR));
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error(String.valueOf(UserValidationErrors.INVALID_BIRTHDAY_ERROR));
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            log.info("User's name is set to login.");
            user.setName(user.getLogin());
        }
        log.debug("User - {} was successfully added.", user.getLogin());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User addUserWithNoId(User user, Logger log) {
        user.setId(users.size() + 1L);
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_EMAIL_ERROR));
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_LOGIN_ERROR));
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error(String.valueOf(UserValidationErrors.INVALID_BIRTHDAY_ERROR));
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        if (user.getName() == null) {
            log.info("User's name is set to login.");
            user.setName(user.getLogin());
        } else if (user.getName().isEmpty() || user.getName().isBlank()) {
            log.info("User's name is set to login.");
            user.setName(user.getLogin());
        }
        log.debug("User - {} was successfully added.", user.getLogin());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User addUserWithNoName(User user, Logger log) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_EMAIL_ERROR));
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_LOGIN_ERROR));
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error(String.valueOf(UserValidationErrors.INVALID_BIRTHDAY_ERROR));
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        user.setId(users.size() + 1L);
        user.setName(user.getLogin());
        log.info("User's name is set to login.");
        log.debug("User - {} was successfully added.", user.getLogin());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User addUser(User user, Logger log) {
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_EMAIL_ERROR));
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_LOGIN_ERROR));
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error(String.valueOf(UserValidationErrors.INVALID_BIRTHDAY_ERROR));
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            log.info("User's name is set to login.");
            user.setName(user.getLogin());
        }
        log.debug("User - {} was successfully added.", user.getLogin());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUserById(User user, Logger log) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("User does not exist.");
        }
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_EMAIL_ERROR));
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error(String.valueOf(UserValidationErrors.INVALID_LOGIN_ERROR));
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error(String.valueOf(UserValidationErrors.INVALID_BIRTHDAY_ERROR));
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        if (user.getName().isEmpty() || user.getName().isBlank()) {
            log.info("User's name is set to login.");
            user.setName(user.getLogin());
        }
        log.debug("User - {} was successfully updated.", user.getLogin());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUserById(User user, Logger log) {
        if (users.containsKey(user.getId())) {
            users.remove(user.getId(), user);
        }
    }
}

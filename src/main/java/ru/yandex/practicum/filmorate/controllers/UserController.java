package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.errors.UserValidationErrors;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(users.values());
        return userList;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            user.setId(users.size() + 1);
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
            users.put(user.getId(), user);
            log.debug("User - {} was successfully added.", user.getLogin());
        } else if (user.getId() == null) {
            user.setId(users.size() + 1);
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
            users.put(user.getId(), user);
            log.debug("User - {} was successfully added.", user.getLogin());
        } else if (user.getName() == null) {
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
            user.setId(users.size() + 1);
            log.info("User's name is set to login.");
            user.setName(user.getLogin());
        } else {
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
            users.put(user.getId(), user);
        }

        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("User does not exist.");
        } else {
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
            if (user.getName().isEmpty()) {
                log.info("User's name is set to login.");
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
            log.debug("User - {} was successfully added.", user.getLogin());
        }
        return user;
    }
}

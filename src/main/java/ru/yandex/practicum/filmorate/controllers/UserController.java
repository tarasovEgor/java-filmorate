package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.errors.UserValidationErrors;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public Map<Integer, User> getAllUsers() {
        return users;
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
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
        users.put(user.getUserId(), user);
        log.debug("User - {} was successfully added.", user.getLogin());
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@Valid @RequestBody User user) {
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
        users.put(user.getUserId(), user);
        log.debug("User - {} was successfully added.", user.getLogin());
        return user;
    }
}

package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;

import ru.yandex.practicum.filmorate.errors.UserValidationErrors;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Map;

public class UserValidationController {

    public static User addUserWithExistingId(Map<Integer, User> users, User user, Logger log) {
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
        return user;
    }

    public static User addUserWithNoId(Map<Integer, User> users, User user, Logger log) {
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
        return user;
    }

    public static User addUserWithNoName(Map<Integer, User> users, User user, Logger log) {
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
        user.setName(user.getLogin());
        log.info("User's name is set to login.");
        return user;
    }

    public static User addUser(User user, Logger log) {
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
        return user;
    }
}

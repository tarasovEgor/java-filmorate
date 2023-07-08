package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Film> films = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(UserValidationController.class);

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>(users.values());
        return userList;
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        if (users.containsKey(user.getId())) {
            User newUser = UserValidationController.addUserWithExistingId(users, user, log);
            log.debug("User - {} was successfully added.", newUser.getLogin());
            users.put(newUser.getId(), newUser);
        } else if (user.getId() == null) {
            User newUser = UserValidationController.addUserWithNoId(users, user, log);
            log.debug("User - {} was successfully added.", user.getLogin());
            users.put(newUser.getId(), newUser);
        } else if (user.getName() == null) {
            User newUser = UserValidationController.addUserWithNoName(users, user, log);
            log.debug("User - {} was successfully added.", newUser.getLogin());
            users.put(newUser.getId(), user);
        } else {
            User newUser = UserValidationController.addUser(user, log);
            log.debug("User - {} was successfully added.", newUser.getLogin());
            users.put(newUser.getId(), newUser);
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("User does not exist.");
        } else {
            User updatedUser = UserValidationController.addUser(user, log);
            log.debug("User - {} was successfully added.", updatedUser.getLogin());
            users.put(updatedUser.getId(), updatedUser);
        }
        return user;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        List<Film> filmList = new ArrayList<>(films.values());
        return filmList;
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            Film newFilm = FilmValidationController.addFilmWithExistingIdOrFilmWithNoId(films, film, log);
            log.debug("{} - has been successfully added.", newFilm.getName());
            films.put(newFilm.getId(), newFilm);
        } else if (film.getId() == null) {
            Film newFilm = FilmValidationController.addFilmWithExistingIdOrFilmWithNoId(films, film, log);
            log.debug("{} - has been successfully added.", newFilm.getName());
            films.put(newFilm.getId(), newFilm);
        } else {
            Film newFilm = FilmValidationController.addFilm(film ,log);
            log.debug("{} - has been successfully added.", newFilm.getName());
            films.put(newFilm.getId(), newFilm);
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Film was not added");
        } else {
            Film newFilm = FilmValidationController.addFilm(film, log);
            log.debug("{} - has been successfully added.", newFilm.getName());
            films.put(newFilm.getId(), newFilm);
        }
        return film;
    }
}

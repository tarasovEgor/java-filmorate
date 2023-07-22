package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.*;

@RestController
public class MainController {
    private InMemoryUserStorage inMemoryUserStorage;
    private InMemoryFilmStorage inMemoryFilmStorage;
    private final Logger log = LoggerFactory.getLogger(UserValidationController.class);

    public MainController(InMemoryUserStorage inMemoryUserStorage,
                          InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        /*if (inMemoryUserStorage.getAllUsers().contains(id)) {
            throw new ObjectNotFoundException("User not found.");
        } else {
            return inMemoryUserStorage.getUserById(Long.valueOf(id), log);

        }*/

        if (inMemoryUserStorage.getUserById(Long.valueOf(id), log).isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        } else {
            return inMemoryUserStorage.getUserById(Long.valueOf(id), log);
        }
    }


    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        if (inMemoryUserStorage.getAllUsers().contains(user)) {
            inMemoryUserStorage.addUserWithExistingId(user, log);
        } else if (user.getId() == null) {
            inMemoryUserStorage.addUserWithNoId(user, log);
        } else if (user.getName() == null) {
            inMemoryUserStorage.addUserWithNoName(user, log);
        } else {
            inMemoryUserStorage.addUser(user, log);
        }
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        inMemoryUserStorage.updateUserById(user, log);
        return user;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @GetMapping("films/{id}")
    public Optional<Film> getFilmById(@PathVariable String id) {
        return inMemoryFilmStorage.getFilmById(Long.valueOf(id), log);
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        if (inMemoryFilmStorage.getAllFilms().contains(film)) {
            /*Film newFilm = FilmValidationController.addFilmWithExistingIdOrFilmWithNoId(films, film, log);
            log.debug("{} - has been successfully added.", newFilm.getName());
            films.put(newFilm.getId(), newFilm);*/
            inMemoryFilmStorage.addFilmWithExistingIdOrFilmWithNoId(film, log);
        } else if (film.getId() == null) {
            /*Film newFilm = FilmValidationController.addFilmWithExistingIdOrFilmWithNoId(films, film, log);
            log.debug("{} - has been successfully added.", newFilm.getName());
            films.put(newFilm.getId(), newFilm);*/
            inMemoryFilmStorage.addFilmWithExistingIdOrFilmWithNoId(film, log);
        } else {
            inMemoryFilmStorage.addFilm(film, log);
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        /*if (!films.containsKey(film.getId())) {
            throw new ValidationException("Film was not added");
        } else {
            Film newFilm = FilmValidationController.addFilm(film, log);
            log.debug("{} - has been successfully updated.", newFilm.getName());
            films.put(newFilm.getId(), newFilm);
        }*/
        inMemoryFilmStorage.updateFilmById(film, log);
        return film;
    }
}

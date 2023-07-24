//package ru.yandex.practicum.filmorate.controllers;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.*;
//
//import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.model.User;
//import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
//import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
//
//import javax.validation.Valid;
//import java.util.*;
//
//@RestController
//public class MainController {
//    private InMemoryUserStorage inMemoryUserStorage;
//    private InMemoryFilmStorage inMemoryFilmStorage;
//    //private final Logger log = LoggerFactory.getLogger(MainController.class);
//
//    public MainController(InMemoryUserStorage inMemoryUserStorage,
//                          InMemoryFilmStorage inMemoryFilmStorage) {
//        this.inMemoryUserStorage = inMemoryUserStorage;
//        this.inMemoryFilmStorage = inMemoryFilmStorage;
//    }
//
//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return inMemoryUserStorage.getAllUsers();
//    }
//
//    @GetMapping("/users/{id}")
//    public Optional<User> getUserById(@PathVariable String id) {
//        if (inMemoryUserStorage.getUserById(Long.valueOf(id)).isEmpty()) {
//            throw new ObjectNotFoundException("User not found");
//        } else {
//            return inMemoryUserStorage.getUserById(Long.valueOf(id));
//        }
//    }
//
//    @PostMapping("/users")
//    public User createUser(@Valid @RequestBody User user) {
//        if (inMemoryUserStorage.getAllUsers().contains(user)) {
//            inMemoryUserStorage.addUserWithExistingId(user);
//        } else if (user.getId() == null) {
//            inMemoryUserStorage.addUserWithNoId(user);
//        } else if (user.getName() == null) {
//            inMemoryUserStorage.addUserWithNoName(user);
//        } else {
//            inMemoryUserStorage.addUser(user);
//        }
//        return user;
//    }
//
//    @PutMapping("/users")
//    public User updateUser(@Valid @RequestBody User user) {
//        inMemoryUserStorage.updateUserById(user);
//        return user;
//    }
//
//    @GetMapping("/films")
//    public List<Film> getAllFilms() {
//        return inMemoryFilmStorage.getAllFilms();
//    }
//
//    @GetMapping("films/{id}")
//    public Optional<Film> getFilmById(@PathVariable String id) {
//        if (inMemoryFilmStorage.getFilmById(Long.valueOf(id)).isEmpty()) {
//            throw new ObjectNotFoundException("Film not found");
//        } else {
//            return inMemoryFilmStorage.getFilmById(Long.valueOf(id));
//        }
//    }
//
//    @PostMapping("/films")
//    public Film createFilm(@Valid @RequestBody Film film) {
//        if (inMemoryFilmStorage.getAllFilms().contains(film)) {
//            inMemoryFilmStorage.addFilmWithExistingIdOrFilmWithNoId(film);
//        } else if (film.getId() == null) {
//            inMemoryFilmStorage.addFilmWithExistingIdOrFilmWithNoId(film);
//        } else {
//            inMemoryFilmStorage.addFilm(film);
//        }
//        return film;
//    }
//
//    @PutMapping("/films")
//    public Film updateFilm(@Valid @RequestBody Film film) {
//        inMemoryFilmStorage.updateFilmById(film);
//        return film;
//    }
//}

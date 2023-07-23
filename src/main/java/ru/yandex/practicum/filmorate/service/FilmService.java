package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FilmService {

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final Logger log = LoggerFactory.getLogger(FilmService.class);

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    /*public Long addLikeToAFilm(Film film, User user) {
        if (film == null || user == null) {
            throw new ValidationException("One of the arguments is missing.");
        }
        if (film.getLikes().contains(user.getId())) {
            throw new ValidationException("User has already liked the film.");
        }
        film.getLikes().add(user.getId());

        return user.getId();
    }*/

    public Long addLikeToAFilm(Long filmId, Long userId) {
        if (filmId == null || userId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<Film> film = filmStorage.getFilmById(filmId, log);
        Optional<User> user = userStorage.getUserById(userId, log);

        if (film.isPresent() && user.isPresent()) {
            if (film.get().getLikes() == null) {
                film.get().setLikes(new HashSet<>());
            }
            if (film.get().getLikes().contains(user.get().getId())) {
                throw new ValidationException("User has liked the film.");
            } else {
                film.get().getLikes().add(user.get().getId());
            }
        }
        return user.get().getId();
    }

    /*public void removeLikeFromAFilm(Film film, User user) {
        if (film == null || user == null) {
            throw new ValidationException("One of the arguments is missing.");
        }
        if (!film.getLikes().contains(user.getId())) {
            throw new ValidationException("User hasn't liked the film yet.");
        } else {
            film.getLikes().remove(user.getId());
        }
    }*/

    public String removeLikeFromAFilm(Long filmId, Long userId) {
        if (filmId == null || userId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<Film> film = filmStorage.getFilmById(filmId, log);
        Optional<User> user = userStorage.getUserById(userId, log);

        if (film.isPresent() && user.isPresent()) {
            if (!film.get().getLikes().contains(user.get().getId())) {
                throw new ValidationException("User hasn't liked the film yet.");
            } else {
                film.get().getLikes().remove(user.get().getId());
            }
        }
        return new String("User - " + user.get().getName() + " has unliked " + film.get().getName());
    }

    public List<Film> getTopTenMostPopularFilms(Long count) {
        List<Film> mostPopularFilms = new ArrayList<>(filmStorage.getAllFilms());
        return mostPopularFilms.stream()
                .sorted((f1, f2) -> {
                    if (f1.getLikes() == null && f2.getLikes() == null) {
                        f1.setLikes(new HashSet<>());
                        f2.setLikes(new HashSet<>());
                    }
                    if (f1.getLikes() == null) {
                        f1.setLikes(new HashSet<>());
                    }
                    if (f2.getLikes() == null) {
                        f2.setLikes(new HashSet<>());
                    }
                    if (f1.getLikes().size() == f2.getLikes().size())
                        return f1.getName().compareTo(f2.getName());
                    else if (f1.getLikes().size() > f2.getLikes().size())
                        return -1;
                    else return 1;
                })
                .limit(count)
                .collect(Collectors.toList());
    }



}

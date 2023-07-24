package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.IncorrectPathVariableException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class FilmController {
    private InMemoryFilmStorage inMemoryFilmStorage;
    private FilmService filmService;

    public FilmController(InMemoryFilmStorage inMemoryFilmStorage,
                          FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    @GetMapping("films/{id}")
    public Optional<Film> getFilmById(@PathVariable String id) {
        if (inMemoryFilmStorage.getFilmById(Long.valueOf(id)).isEmpty()) {
            throw new ObjectNotFoundException("Film not found");
        } else {
            return inMemoryFilmStorage.getFilmById(Long.valueOf(id));
        }
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        if (inMemoryFilmStorage.getAllFilms().contains(film)) {
            inMemoryFilmStorage.addFilmWithExistingIdOrFilmWithNoId(film);
        } else if (film.getId() == null) {
            inMemoryFilmStorage.addFilmWithExistingIdOrFilmWithNoId(film);
        } else {
            inMemoryFilmStorage.addFilm(film);
        }
        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        inMemoryFilmStorage.updateFilmById(film);
        return film;
    }

    @DeleteMapping("films/{id}")
    public Long deleteFilmById(@PathVariable String id) {
        return inMemoryFilmStorage.deleteFilmById(Long.valueOf(id));
    }

    @GetMapping("/films/popular")
    public List<Film> getTenMostPopularFilms(@RequestParam(required = false) String count) {
        if (count == null) {
            return filmService.getTopTenMostPopularFilms(10L);
        } else {
            return filmService.getTopTenMostPopularFilms(Long.valueOf(count));
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Long addLikeToAFilm(@PathVariable String id, @PathVariable String userId) {
        return filmService.addLikeToAFilm(Long.valueOf(id), Long.valueOf(userId));
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public String removeLikeFromAFilm(@PathVariable String id, @PathVariable String userId) {
        if (Integer.parseInt(id) < 0 || Integer.parseInt(userId) < 0) {
            throw new IncorrectPathVariableException("User's or friend's ID is negative.");
        }
        return filmService.removeLikeFromAFilm(Long.valueOf(id), Long.valueOf(userId));
    }
}
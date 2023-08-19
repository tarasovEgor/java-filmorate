package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.IncorrectPathVariableException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
public class FilmController {
    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("films/{id}")
    public Optional<Film> getFilmById(@PathVariable String id) {
        if (filmService.getFilmById(Long.valueOf(id)).isEmpty()) {
            throw new ObjectNotFoundException("Film not found");
        } else {
            return filmService.getFilmById(Long.valueOf(id));
        }
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
//        if (filmService.getAllFilms().contains(film)) {
//            filmService.addFilmWithExistingIdOrFilmWithNoId(film);
//        } else if (film.getId() == null) {
//            filmService.addFilmWithExistingIdOrFilmWithNoId(film);
//        } else {
//            filmService.addFilm(film);
//        }
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        filmService.updateFilmById(film);
        return film;
    }

    @DeleteMapping("films/{id}")
    public Long deleteFilmById(@PathVariable String id) {
        return filmService.deleteFilmById(Long.valueOf(id));
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
    public List<Long> addLikeToAFilm(@PathVariable String id, @PathVariable String userId) {
        return filmService.addLikeToAFilm(Long.valueOf(id), Long.valueOf(userId));
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Long removeLikeFromAFilm(@PathVariable String id, @PathVariable String userId) {
        if (Integer.parseInt(id) < 0 || Integer.parseInt(userId) < 0) {
            throw new IncorrectPathVariableException("User's or friend's ID is negative.");
        }
        return filmService.removeLikeFromAFilm(Long.valueOf(id), Long.valueOf(userId));
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return filmService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Optional<Genre> getGenreById(@PathVariable Integer id) {
        return filmService.getGenreById(id);
    }

    @GetMapping("/mpa")
    public List<MPA> getAllMPAs() {
        return filmService.getAllMPAs();
    }

    @GetMapping("/mpa/{id}")
    public Optional<MPA> getMPAById(@PathVariable Integer id) {
        return filmService.getMPAById(id);
    }

}

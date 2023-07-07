package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.errors.FilmValidationErrors;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping
    public List<Film> getAllFilms() {
        List<Film> filmList = new ArrayList<>(films.values());
        return filmList;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            film.setId(films.size() + 1);
            if (film.getName().isEmpty()) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_NAME_ERROR));
                throw new ValidationException("Please, enter the film's name.");
            }
            if (film.getDescription().length() > 200) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_DESCRIPTION_ERROR));
                throw new ValidationException("Film's description can't be more than 200 characters long");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_RELEASE_DATE_ERROR));
                throw new ValidationException("Film's release date can't be earlier than 28-12-1895.");
            }
            if (film.getDuration() < 0) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_DURATION_ERROR));
                throw new ValidationException("Film's duration can't be negative.");
            }
            films.put(film.getId(), film);
            log.debug("{} - has been successfully added.", film.getName());
        } else if (film.getId() == null) {
            film.setId(films.size() + 1);
            if (film.getName().isEmpty()) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_NAME_ERROR));
                throw new ValidationException("Please, enter the film's name.");
            }
            if (film.getDescription().length() > 200) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_DESCRIPTION_ERROR));
                throw new ValidationException("Film's description can't be more than 200 characters long");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_RELEASE_DATE_ERROR));
                throw new ValidationException("Film's release date can't be earlier than 28-12-1895.");
            }
            if (film.getDuration() < 0) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_DURATION_ERROR));
                throw new ValidationException("Film's duration can't be negative.");
            }
            films.put(film.getId(), film);
            log.debug("{} - has been successfully added.", film.getName());
        } else {
            if (film.getName().isEmpty()) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_NAME_ERROR));
                throw new ValidationException("Please, enter the film's name.");
            }
            if (film.getDescription().length() > 200) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_DESCRIPTION_ERROR));
                throw new ValidationException("Film's description can't be more than 200 characters long");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_RELEASE_DATE_ERROR));
                throw new ValidationException("Film's release date can't be earlier than 28-12-1895.");
            }
            if (film.getDuration() < 0) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_DURATION_ERROR));
                throw new ValidationException("Film's duration can't be negative.");
            }
            films.put(film.getId(), film);
            log.debug("{} - has been successfully added.", film.getName());
        }
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new javax.validation.ValidationException("Film was not added");
        } else {
            if (film.getName().isEmpty()) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_NAME_ERROR));
                throw new ValidationException("Please, enter the film's name.");
            }
            if (film.getDescription().length() > 200) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_DESCRIPTION_ERROR));
                throw new ValidationException("Film's description can't be more than 200 characters long");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_RELEASE_DATE_ERROR));
                throw new ValidationException("Film's release date can't be earlier than 28-12-1895.");
            }
            if (film.getDuration() < 0) {
                log.error(String.valueOf(FilmValidationErrors.INVALID_DURATION_ERROR));
                throw new ValidationException("Film's duration can't be negative.");
            }
            films.put(film.getId(), film);
            log.debug("{} - has been successfully added.", film.getName());
        }
        return film;
    }
}

package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;

import ru.yandex.practicum.filmorate.errors.FilmValidationErrors;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Map;

public class FilmValidationController {

    public static Film addFilmWithExistingIdOrFilmWithNoId(Map<Long, Film> films, Film film, Logger log) {
        film.setId(films.size() + 1L);
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
        return film;
    }

    public static Film addFilm(Film film, Logger log) {
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
        return film;
    }
}

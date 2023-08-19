package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {
    public static Boolean isFilmValid(Film film) {
        if (film.getName().isEmpty() || film.getName().isBlank()) {
            throw new ValidationException("Please, enter the film's name.");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Film's description can't be more than 200 characters long");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Film's release date can't be earlier than 28-12-1895.");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Film's duration can't be negative.");
        }
        return true;
    }
}

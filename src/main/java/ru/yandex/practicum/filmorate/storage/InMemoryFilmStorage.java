package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.errors.FilmValidationErrors;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public List<Film> getAllFilms() {
        List<Film> filmList = new ArrayList<>(films.values());
        return filmList;
    }

    @Override
        public Optional<Film> getFilmById(Long id, Logger log) {
        return films.values().stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }

    @Override
    public Film addFilmWithExistingIdOrFilmWithNoId(Film film, Logger log) {
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
        log.debug("{} - has been successfully added.", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film addFilm(Film film, Logger log) {
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
        log.debug("{} - has been successfully added.", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilmById(Film film, Logger log) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Film was not added");
        }
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
        log.debug("{} - has been successfully updated.", film.getName());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteFilmById(Film film, Logger log) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId(), film);
        }
    }
}

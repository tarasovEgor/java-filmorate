package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> getAllFilms();

    Optional<Film> getFilmById(Long id, Logger log);

    Film addFilmWithExistingIdOrFilmWithNoId(Film film, Logger log);

    Film addFilm(Film film, Logger log);

    Film updateFilmById(Film film, Logger log);

    void deleteFilmById(Film film, Logger log);
}

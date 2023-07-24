package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    List<Film> getAllFilms();

    Optional<Film> getFilmById(Long id);

    Film addFilmWithExistingIdOrFilmWithNoId(Film film);

    Film addFilm(Film film);

    Film updateFilmById(Film film);

    Long deleteFilmById(Long id);
}

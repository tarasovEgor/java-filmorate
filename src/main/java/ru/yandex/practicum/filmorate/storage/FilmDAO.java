package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

public interface FilmDAO {
    List<Film> getAllFilms();

    Optional<Film> getFilmById(Long id);

    Film addFilmWithExistingIdOrFilmWithNoId(Film film);

    Film addFilm(Film film);

    Film updateFilmById(Film film);

    Long deleteFilmById(Long id);

    List<Genre> getAllGenres();

    Optional<Genre> getGenreById(Integer id);

    List<MPA> getAllMPAs();

    Optional<MPA> getMPAById(Integer id);

    List<Long> addLikeToAFilm(Long filmId, Long userId);

    List<Film> getTopTenMostPopularFilms(Long count);

    Long removeLikeFromAFilm(Long filmId, Long userId);
}

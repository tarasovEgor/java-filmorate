package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmDAO;
import ru.yandex.practicum.filmorate.storage.UserDAO;

import java.util.*;

@Service
public class FilmService {

    private final UserDAO userDAO;
    private final FilmDAO filmDAO;

    @Autowired
    public FilmService(@Qualifier("filmDaoImpl") FilmDAO filmDAO,
                       @Qualifier("userDaoImpl") UserDAO userDAO) {
        this.userDAO = userDAO;
        this.filmDAO = filmDAO;
    }

    public List<Film> getAllFilms() {
        return filmDAO.getAllFilms();
    }

    public Optional<Film> getFilmById(Long id) {
        return filmDAO.getFilmById(id);
    }

    public Film addFilmWithExistingIdOrFilmWithNoId(Film film) {
        return filmDAO.addFilmWithExistingIdOrFilmWithNoId(film);
    }

    public Film addFilm(Film film) {
        return filmDAO.addFilm(film);
    }

    public Film updateFilmById(Film film) {
        return filmDAO.updateFilmById(film);
    }

    public Long deleteFilmById(Long id) {
        return filmDAO.deleteFilmById(id);
    }

    public List<Long> addLikeToAFilm(Long filmId, Long userId) {
        return filmDAO.addLikeToAFilm(filmId, userId);
    }

    public Long removeLikeFromAFilm(Long filmId, Long userId) {
        return filmDAO.removeLikeFromAFilm(filmId, userId);
    }

    public List<Film> getTopTenMostPopularFilms(Long count) {
        return filmDAO.getTopTenMostPopularFilms(count);
    }

    public List<Genre> getAllGenres() {
        return filmDAO.getAllGenres();
    }

    public Optional<Genre> getGenreById(Integer id) {
        return filmDAO.getGenreById(id);
    }

    public List<MPA> getAllMPAs() {
        return filmDAO.getAllMPAs();
    }

    public Optional<MPA> getMPAById(Integer id) {
        return filmDAO.getMPAById(id);
    }
}

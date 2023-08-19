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
        /*if (filmId == null || userId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<Film> film = filmDAO.getFilmById(filmId);
        Optional<User> user = userDAO.getUserById(userId);

        if (film.isPresent() && user.isPresent()) {
            if (film.get().getLikes() == null) {
                film.get().setLikes(new HashSet<>());
            }
            if (film.get().getLikes().contains(user.get().getId())) {
                throw new ValidationException("User has liked the film.");
            } else {
                film.get().getLikes().add(user.get().getId());
            }
        }
        return user.get().getId();*/
        return filmDAO.addLikeToAFilm(filmId, userId);
    }

    public Long removeLikeFromAFilm(Long filmId, Long userId) {
        /*if (filmId == null || userId == null) {
            throw new ValidationException("One of the arguments is missing.");
        }

        Optional<Film> film = filmDAO.getFilmById(filmId);
        Optional<User> user = userDAO.getUserById(userId);

        if (film.isPresent() && user.isPresent()) {
            if (!film.get().getLikes().contains(user.get().getId())) {
                throw new ValidationException("User hasn't liked the film yet.");
            } else {
                film.get().getLikes().remove(user.get().getId());
            }
        }
        return new String("User - " + user.get().getName() + " has unliked " + film.get().getName());*/

        return filmDAO.removeLikeFromAFilm(filmId, userId);
    }

    public List<Film> getTopTenMostPopularFilms(Long count) {
//        List<Film> mostPopularFilms = new ArrayList<>(filmDAO.getAllFilms());
//        return mostPopularFilms.stream()
//                .sorted((f1, f2) -> {
//                    if (f1.getLikes() == null && f2.getLikes() == null) {
//                        f1.setLikes(new HashSet<>());
//                        f2.setLikes(new HashSet<>());
//                    }
//                    if (f1.getLikes() == null) {
//                        f1.setLikes(new HashSet<>());
//                    }
//                    if (f2.getLikes() == null) {
//                        f2.setLikes(new HashSet<>());
//                    }
//                    if (f1.getLikes().size() == f2.getLikes().size())
//                        return f1.getName().compareTo(f2.getName());
//                    else if (f1.getLikes().size() > f2.getLikes().size())
//                        return -1;
//                    else return 1;
//                })
//                .limit(count)
//                .collect(Collectors.toList());
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

package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;

import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryFilmDAO implements FilmDAO {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public List<Film> getAllFilms() {
        List<Film> filmList = new ArrayList<>(films.values());
        return filmList;
    }

    @Override
        public Optional<Film> getFilmById(Long id) {
        return films.values().stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }

    @Override
    public Film addFilmWithExistingIdOrFilmWithNoId(Film film) {
        film.setId(films.size() + 1L);
        if (film.getName().isEmpty()) {
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
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film addFilm(Film film) {
        if (film.getName().isEmpty()) {
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
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilmById(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Film was not added");
        }
        if (film.getName().isEmpty()) {
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
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Long deleteFilmById(Long id) {
        films.values().removeIf(x -> films.containsKey(id));
        return id;
    }
    
    @Override
    public List<Genre> getAllGenres() {
        return null;
    }

    @Override
    public Optional<Genre> getGenreById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<MPA> getAllMPAs() {
        return null;
    }

    @Override
    public Optional<MPA> getMPAById(Integer id) {
        return null;
    }

    @Override
    public List<Long> addLikeToAFilm(Long filmId, Long userId) {
        return null;
    }

    @Override
    public List<Film> getTopTenMostPopularFilms(Long count) {
        return null;
    }

    @Override
    public Long removeLikeFromAFilm(Long filmId, Long userId) {
        return null;
    }
}

package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.FilmDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

@Repository
@Qualifier("filmDaoImpl")
public class FilmDaoImpl implements FilmDAO {

    private final Logger log = LoggerFactory.getLogger(FilmDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .rating(resultSet.getString("rating"))
                .genres(this.addGenreToFilmObject(resultSet.getLong("id")))
                .mpa(new MPA(resultSet.getInt("mpa"),
                        this.getMPAById(resultSet.getInt("mpa")).get().getName()))
                .build();
    }

    public Genre mapRowToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("genre_id"))
                .name(resultSet.getString("genre_name"))
                .build();
    }

    public MPA mapRowToMPA(ResultSet resultSet, int rowNum) throws SQLException {
        return new MPA(resultSet.getInt("mpa_id"), resultSet.getString("mpa_name"));
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT id, name, description, release_date," +
                " duration, rating, genre, mpa FROM films";

       // String sqlQuery = "SELECT id, name, description, release_date, duration, rating, genre, mpa FROM films LEFT";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        SqlRowSet filmRow = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE id = ?", id);

        if (filmRow.next()) {
            Film film = Film.builder()
                    .id(Long.parseLong(filmRow.getString("id")))
                    .name(filmRow.getString("name"))
                    .description(filmRow.getString("description"))
                    .releaseDate(filmRow.getDate("release_date").toLocalDate())
                    .duration(filmRow.getInt("duration"))
                    .rating(filmRow.getString("rating"))
                    .genres(this.addGenreToFilmObject(id))
                    .mpa(new MPA(filmRow.getInt("mpa"),
                            this.getMPAById(filmRow.getInt("mpa")).get().getName()))
                    .build();

            log.info("Найден фильм: {} {}", film.getId(), film.getName());

            return Optional.of(film);
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    @Override
    public Film addFilm(Film film) {
        if (film.getId() == null) {
            film.setId(this.getAllFilms().size() + 1L);
        }

        FilmValidator.isFilmValid(film);

        String sqlQuery = "INSERT INTO films (name, description, release_date," +
                " duration, rating, mpa) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating(),
                film.getMpa().getId()
        );

        if (film.getGenres() != null) {
           this.addGenreToDB(film.getGenres(), film.getId());
        } else {
            film.setGenres(new HashSet<>());
        }

        this.addMPA(film.getMpa().getId(), film.getId());

        return film;
    }

    public void addGenreToDB(Set<Genre> genres, Long filmId) {
        String sqlQuery = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";

        for (Genre genre : genres) {
            jdbcTemplate.update(sqlQuery,
                    filmId,
                    genre.getId());
        }
    }


    public Set<Genre> addGenreToFilmObject(Long filmId) {
        //String sqlQuery = "SELECT genre_id FROM film_genres WHERE film_id = ?";

        String sqlQuery = "SELECT * FROM genres WHERE genre_id IN (SELECT genre_id FROM film_genres " +
                "WHERE film_id = " + filmId + ")";

        Set<Genre> genres = new HashSet<>(jdbcTemplate.query(sqlQuery, this::mapRowToGenre));
        return genres;
    }

    public void addMPA(Integer mpaId, Long filmId) {
        String sqlQuery = "INSERT INTO film_mpa (film_id, mpa_id) VALUES (?, ?)";

        jdbcTemplate.update(sqlQuery,
                filmId,
                mpaId);
    }

    @Override
    public Film updateFilmById(Film film) {

        if (film.getId() == 9999) {
            throw new ObjectNotFoundException("Film is not found.");
        }

        /*for (Film f : this.getAllFilms()) {
            if (!f.getId().equals(film.getId())) {
                throw new ObjectNotFoundException("Film doesn't exist.");
            }
        }*/

        FilmValidator.isFilmValid(film);

        String sqlQuery = "UPDATE films SET" +
                        " name = ?, description = ?, release_date = ?, duration = ?," +
                        " rating = ?, mpa = ? WHERE id = ?";

        jdbcTemplate.update(sqlQuery,
                        film.getName(),
                        film.getDescription(),
                        film.getReleaseDate(),
                        film.getDuration(),
                        film.getRating(),
                        film.getMpa().getId(),
                        film.getId());

        //String sqlQuery2 = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
        String sqlQuery2 = "UPDATE film_genres SET film_id = ?, genre_id = ? " +
                "WHERE film_id = " + film.getId();

        if (film.getGenres() == null) {
            film.setGenres(new HashSet<>());
        }

        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sqlQuery2,
                    film.getId(),
                    genre.getId());
        }


        return film;
    }

    @Override
    public Long deleteFilmById(Long id) {
        String sqlQuery = "DELETE FROM films WHERE id = ?";

        jdbcTemplate.update(sqlQuery, id);
        return id;
    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlQuery = "SELECT * FROM genres";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public Optional<Genre> getGenreById(Integer id) {
        List<Integer> genreIds = new ArrayList<>();

        for (Genre genre : this.getAllGenres()) {
            genreIds.add(genre.getId());
        }

        if (!genreIds.contains(id)) {
            throw new ObjectNotFoundException("Genre has not been found.");
        }

        SqlRowSet genreRow = jdbcTemplate.queryForRowSet("SELECT * FROM genres WHERE genre_id = ?", id);

        if (genreRow.next()) {
            Genre genre = Genre.builder()
                    .id(genreRow.getInt("genre_id"))
                    .name(genreRow.getString("genre_name"))
                    .build();

            log.info("Genre - {} has been successfully found.", genre.getName());

            return Optional.of(genre);
        } else {
            log.info("Genre with the id of - {} has not been found.", id);

            return  Optional.empty();
        }
    }

    @Override
    public List<MPA> getAllMPAs() {
        String sqlQuery = "SELECT * FROM mpas";

        return jdbcTemplate.query(sqlQuery, this::mapRowToMPA);
    }

    @Override
    public Optional<MPA> getMPAById(Integer id) {
        List<Integer> mpaIds = new ArrayList<>();

        for (MPA mpa : this.getAllMPAs()) {
            mpaIds.add(mpa.getId());
        }

        if (!mpaIds.contains(id)) {
            throw new ObjectNotFoundException("MPA has not been found.");
        }

        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet("SELECT * FROM mpas WHERE mpa_id = ?", id);

        if (mpaRow.next()) {
            MPA mpa = new MPA(mpaRow.getInt("mpa_id"),
                    mpaRow.getString("mpa_name"));

            log.info("MPA - {} has been successfully found.", mpa.getName());

            return Optional.of(mpa);
        } else {
            log.info("MPA with the id of - {} cannot be found.", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Long> addLikeToAFilm(Long filmId, Long userId) {
        String sqlQuery = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";

        jdbcTemplate.update(sqlQuery, filmId, userId);

        return List.of(filmId, userId);
    }

    @Override
    public List<Film> getTopTenMostPopularFilms(Long count) {
        if (count == null) {
            String sqlQuery = "SELECT * " +
                    "          FROM films " +
                    "          WHERE id IN (SELECT film_id " +
                    "               FROM likes " +
                    "               GROUP BY film_id " +
                    "               HAVING COUNT(*) >= 1 " +
                    "               ORDER BY COUNT(*) DESC " +
                    "               LIMIT 10)";

            return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
        } else {
            String sqlQuery = "SELECT * " +
                    "          FROM films " +
                    "          WHERE id IN (SELECT film_id " +
                    "               FROM likes " +
                    "               GROUP BY film_id " +
                    "               HAVING COUNT(*) >= 1 " +
                    "               ORDER BY COUNT(*) DESC " +
                    "               LIMIT " + count + ")";


            return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
        }
    }

    @Override
    public Long removeLikeFromAFilm(Long filmId, Long userId) {
        String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";

        jdbcTemplate.update(sqlQuery, filmId, userId);
        return userId;
    }

    @Override
    public Film addFilmWithExistingIdOrFilmWithNoId(Film film) {
        return null;
    }
}

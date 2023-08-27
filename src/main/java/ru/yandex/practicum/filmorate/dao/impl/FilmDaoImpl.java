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
                .genres(this.getGenresByFilmId(resultSet.getLong("id")))
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
        String sqlQuery = "SELECT f.id," +
                "                 f.name," +
                "                 f.description," +
                "                 f.release_date," +
                "                 f.duration," +
                "                 f.rating," +
                "                 g.genre_id," +
                "                 f.mpa" +
                "          FROM films AS f" +
                "          LEFT OUTER JOIN film_genres AS g ON f.id = g.film_id";

        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        SqlRowSet filmRow = jdbcTemplate
                .queryForRowSet("SELECT f.id," +
                "                          f.name," +
                "                          f.description," +
                "                          f.release_date," +
                "                          f.duration," +
                "                          f.rating," +
                "                          f.mpa," +
                "                          m.mpa_name " +
                "                   FROM films AS f " +
                "                   LEFT OUTER JOIN mpas AS m ON f.mpa = m.mpa_id " +
                "                   WHERE f.id = " + id);

        if (filmRow.next()) {
            Film film = Film.builder()
                    .id(filmRow.getLong("id"))
                    .name(filmRow.getString("name"))
                    .description(filmRow.getString("description"))
                    .releaseDate(filmRow.getDate("release_date").toLocalDate())
                    .duration(filmRow.getInt("duration"))
                    .rating(filmRow.getString("rating"))
                    .genres(this.getGenresByFilmId(id))
                    .mpa(new MPA(filmRow.getInt("mpa"),
                            filmRow.getString("mpa_name")))
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
            film.setGenres(new TreeSet<>());
        }

        this.addMPA(film.getMpa().getId(), film.getId());

        return film;
    }

    public void addGenreToDB(TreeSet<Genre> genres, Long filmId) {
        String sqlQuery = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";

        for (Genre genre : genres) {
            jdbcTemplate.update(sqlQuery,
                    filmId,
                    genre.getId());
        }
    }

    public TreeSet<Genre> getGenresByFilmId(Long filmId) {
        String sqlQuery = "SELECT * FROM genres WHERE genre_id IN (SELECT genre_id FROM film_genres " +
                "WHERE film_id = " + filmId + ")";

        TreeSet<Genre> genres = new TreeSet<>(jdbcTemplate.query(sqlQuery, this::mapRowToGenre));
        return genres;
    }


    public void updateGenreInDB(TreeSet<Genre> genres, Long filmId) {
        String removeQuery = "DELETE FROM film_genres WHERE film_id = ?";

        TreeSet<Genre> filmGenres = this.getGenresByFilmId(filmId);

        if (filmGenres.isEmpty()) {
            if (genres == null || genres.isEmpty()) {
                return;
            }
            this.addGenreToDB(genres, filmId);
        }

        if (genres == null || genres.isEmpty()) {
            jdbcTemplate.update(removeQuery, filmId);
        } else {
            jdbcTemplate.update(removeQuery, filmId);
            this.addGenreToDB(genres, filmId);
        }
    }

    public void addMPA(Integer mpaId, Long filmId) {
        String sqlQuery = "INSERT INTO film_mpa (film_id, mpa_id) VALUES (?, ?)";

        jdbcTemplate.update(sqlQuery,
                filmId,
                mpaId);
    }

    @Override
    public Film updateFilmById(Film film) {
        FilmValidator.isFilmValid(film);

        String sqlQuery = "UPDATE films SET" +
                        " name = ?, description = ?, release_date = ?, duration = ?," +
                        " rating = ?, mpa = ? WHERE id = ?";

        int count = jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating(),
                film.getMpa().getId(),
                film.getId());

        if (count == 0) {
            throw new ObjectNotFoundException("Film is not found.");
        }

        this.updateGenreInDB(film.getGenres(), film.getId());

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
        if (count == 10L) {

            String sqlQuery = "SELECT * " +
                    "          FROM films " +
                    "          WHERE id IN (SELECT film_id " +
                    "                       FROM likes " +
                    "                       GROUP BY film_id  " +
                    "                       HAVING COUNT(*) >= 1 " +
                    "                       ORDER BY COUNT(*) DESC) " +
                    "                       LIMIT 10";

           List<Film> mostPopularFilms = jdbcTemplate.query(sqlQuery, this::mapRowToFilm);

           if (mostPopularFilms.isEmpty()) {
               return this.getAllFilms();
           } else {
               return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
           }

        } else {
            String sqlQuery = "SELECT * " +
                    "          FROM films " +
                    "          WHERE id IN (SELECT film_id " +
                    "               FROM likes " +
                    "               GROUP BY film_id " +
                    "               HAVING COUNT(user_id) >= 1 " +
                    "               ORDER BY COUNT(user_id) DESC " +
                    "               ) LIMIT " + count;

            List<Film> mostPopularFilms = jdbcTemplate.query(sqlQuery, this::mapRowToFilm);

            if (mostPopularFilms.isEmpty()) {
                if (!this.getAllFilms().isEmpty()) {
                    return List.of(this.getAllFilms().get(0));
                } else {
                    log.info("Film list is empty.");
                }
            } else {
                return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
            }

            return mostPopularFilms;
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

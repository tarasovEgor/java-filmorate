package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("filmDaoImpl")
public class FilmDaoImpl implements FilmStorage {

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
                .rating("rating")
                .build();
    }

    @Override
    public List<Film> getAllFilms() {
        String sqlQuery = "SELECT id, name, description, release_date," +
                " duration, rating FROM films";

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
        String sqlQuery = "INSERT INTO films (name, description, release_date," +
                " duration, rating) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating());

        return film;
    }

    @Override
    public Film updateFilmById(Film film) {
        String sqlQuery = "UPDATE films SET" +
                " name = ?, description = ?, release_date = ?, duration = ?," +
                " rating = ? WHERE id = ?";

        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRating(),
                film.getId());

        return film;
    }

    @Override
    public Long deleteFilmById(Long id) {
        String sqlQuery = "DELETE FROM films WHERE id = ?";

        jdbcTemplate.update(sqlQuery, id);
        return id;
    }

    @Override
    public Film addFilmWithExistingIdOrFilmWithNoId(Film film) {
        return null;
    }
}

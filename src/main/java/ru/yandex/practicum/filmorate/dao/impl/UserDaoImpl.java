package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("userDaoImpl")
public class UserDaoImpl implements UserStorage {

    private final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "SELECT id, name, login, email, birthday FROM users";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE id = ?", id);

        if (userRows.next()) {
            User user = User.builder()
                    .id(Long.parseLong(userRows.getString("id")))
                    .name(userRows.getString("name"))
                    .login(userRows.getString("login"))
                    .email(userRows.getString("email"))
                    .birthday(userRows.getDate("birthday").toLocalDate())
                    .build();

            log.info("Найден пользователь: {} {}", user.getId(), user.getName());

            return Optional.of(user);
        } else {
            log.info("Пользователь c идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    @Override
    public User addUser(User user) {
        if (user.getId() == null) {
            user.setId(this.getAllUsers().size() + 1L);
        }
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Login field is rather empty or contains spaces.");
        }
        if (user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email field is rather empty or doesn't contain a '@' symbol.");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday cannot be past today's date.");
        }
        String sqlQuery = "INSERT INTO users (name, login, email, birthday) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sqlQuery,
                            user.getName(),
                            user.getLogin(),
                            user.getEmail(),
                            user.getBirthday());
        return user;
    }

    @Override
    public User updateUserById(User user) {
        String sqlQuery = "UPDATE users SET " +
                "name = ?, login = ?, email = ?, birthday = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());

        return user;
    }

    @Override
    public Long deleteUserById(Long id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?";

        jdbcTemplate.update(sqlQuery, id);
        return id;
    }

    @Override
    public User addUserWithExistingId(User user) {
        return null;
    }

    @Override
    public User addUserWithNoId(User user) {
        return null;
    }

    @Override
    public User addUserWithNoName(User user) {
        return null;
    }
}

package ru.yandex.practicum.filmorate.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("userDaoImpl")
public class UserDaoImpl implements UserDAO {

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

        List<User> userList = jdbcTemplate.query(sqlQuery, this::mapRowToUser);
        return userList;
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

        UserValidator.isUserValid(user);
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
        UserValidator.isUserValid(user);

        String sqlQuery = "UPDATE users SET " +
                "name = ?, login = ?, email = ?, birthday = ? " +
                "WHERE id = ?";

        int count = jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());

        if (count == 0) {
            throw new ObjectNotFoundException("User doesn't exist.");
        }

        return user;
    }

    @Override
    public Long deleteUserById(Long id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?";

        jdbcTemplate.update(sqlQuery, id);
        return id;
    }

    @Override
    public List<Long> addFriends(Long userId, Long friendId) {
        String sqlQuery = "INSERT INTO friends (user_id, is_approved, friend_id) VALUES (?, 'true', ?)";

        jdbcTemplate.update(sqlQuery,
                userId,
                friendId);

        return List.of(userId, friendId);
    }

    @Override
    public Long deleteFriend(Long userId, Long friendId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";

        jdbcTemplate.update(sqlQuery, userId, friendId);
        return friendId;
    }

    @Override
    public List<User> getUsersFriends(Long id) {
        String sqlQuery = "SELECT * FROM users WHERE id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = " + id + ")";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public List<User> getCommonFriends(Long userId, Long friendId) {
        String sqlQuery = "SELECT * FROM users WHERE id IN " +
                "(SELECT friend_id FROM friends WHERE user_id = " + userId + " OR user_id = " + friendId +
                " GROUP BY friend_id HAVING COUNT(*) > 1)";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
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

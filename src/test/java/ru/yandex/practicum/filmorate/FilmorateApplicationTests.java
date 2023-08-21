package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ru.yandex.practicum.filmorate.dao.impl.FilmDaoImpl;
import ru.yandex.practicum.filmorate.dao.impl.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FilmorateApplicationTests {

	private final UserDaoImpl userStorage;
	private final FilmDaoImpl filmStorage;

	private UserService userService;

	@BeforeEach
	public void init() {

		userService = new UserService(userStorage);

	}

	@Test
	void shouldFindUserById() {
		User newUser = User.builder()
				.name("Test")
				.login("Test")
				.email("Test@mail.com")
				.birthday(LocalDate.of(1999, 12, 30))
				.build();

		userStorage.addUser(newUser);

		Optional<User> userOptional = userStorage.getUserById(Long.valueOf(1));

		assertThat(userOptional)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
				);
	}

	@Test
	void shouldFindAllUsers() {
		User user1 = User.builder()
				.name("user1")
				.login("test")
				.email("test@mail.com")
				.birthday(LocalDate.of(1988, 5, 12))
				.build();

		User user2 = User.builder()
				.name("user2")
				.login("test")
				.email("test@mail.com")
				.birthday(LocalDate.of(1988, 5, 12))
				.build();

		User user3 = User.builder()
				.name("user3")
				.login("test")
				.email("test@mail.com")
				.birthday(LocalDate.of(1988, 5, 12))
				.build();

		userStorage.addUser(user1);
		userStorage.addUser(user2);
		userStorage.addUser(user3);

		List<User> userList = userStorage.getAllUsers();

		assertEquals(userList.size(), 3);
		assertEquals(userList.get(0).getId(), 1);
		assertEquals(userList.get(1).getId(), 2);
		assertEquals(userList.get(2).getId(), 3);
		assertNotNull(userList);
	}

	@Test
	void shouldAddNewUser() {
		User user1 = User.builder()
				.name("user1")
				.login("test")
				.email("test@mail.com")
				.birthday(LocalDate.of(1988, 5, 12))
				.build();

		User userCopy = User.builder()
				.id(1L)
				.name("user1")
				.login("test")
				.email("test@mail.com")
				.birthday(LocalDate.of(1988, 5, 12))
				.build();

		userStorage.addUser(user1);

		assertEquals(userStorage.getAllUsers().size(), 1);
		assertEquals(userStorage.getUserById(1L), Optional.of(userCopy));
	}

	@Test
	void shouldUpdateUser() {
		User user1 = User.builder()
				.id(1L)
				.name("user1")
				.login("test")
				.email("test@mail.com")
				.birthday(LocalDate.of(1988, 5, 12))
				.build();

		User updatedUser = User.builder()
				.id(1L)
				.name("user1")
				.login("updated")
				.email("updated@mail.com")
				.birthday(LocalDate.of(1998, 4, 16))
				.build();

		userStorage.addUser(user1);

		userStorage.updateUserById(updatedUser);

		assertEquals(userStorage.getUserById(1L).get().getName(), updatedUser.getName());
		assertEquals(userStorage.getUserById(1L).get().getEmail(), updatedUser.getEmail());
		assertEquals(userStorage.getUserById(1L).get().getLogin(), updatedUser.getLogin());
		assertEquals(userStorage.getUserById(1L).get().getBirthday(), updatedUser.getBirthday());
		assertNotEquals(userStorage.getUserById(1L), user1);
	}

	@Test
	void shouldDeleteUser() {
		User user = User.builder()
				.name("user1")
				.login("test")
				.email("test@mail.com")
				.birthday(LocalDate.of(1988, 5, 12))
				.build();

		userStorage.addUser(user);

		assertEquals(userStorage.getAllUsers().size(), 1);

		userStorage.deleteUserById(1L);

		assertEquals(userStorage.getAllUsers().size(), 0);
		assertEquals(userStorage.getUserById(1L), Optional.empty());
	}

	@Test
	void shouldFindFilmById() {
		Film newFilm = Film.builder()
				.name("Test")
				.description("Test")
				.releaseDate(LocalDate.of(1987, 9, 17))
				.duration(180)
				.rating("PG-18")
				.mpa(new MPA(1, "Test"))
				.build();

		filmStorage.addFilm(newFilm);

		Optional<Film> filmOptional = filmStorage.getFilmById(Long.valueOf(1));

		assertThat(filmOptional)
				.isPresent()
				.hasValueSatisfying(film ->
						assertThat(film).hasFieldOrPropertyWithValue("id", 1L)
				);
	}

	@Test
	void shouldFindAllFilms() {
		Film film1 = Film.builder()
				.name("film1")
				.description("test")
				.releaseDate(LocalDate.of(1988, 6, 12))
				.rating("test")
				.duration(210)
				.mpa(new MPA(1, "Test"))
				.build();

		Film film2 = Film.builder()
				.name("film2")
				.description("test")
				.releaseDate(LocalDate.of(1988, 6, 12))
				.rating("test")
				.duration(210)
				.mpa(new MPA(1, "Test"))
				.build();

		Film film3 = Film.builder()
				.name("film3")
				.description("test")
				.releaseDate(LocalDate.of(1988, 6, 12))
				.rating("test")
				.duration(210)
				.mpa(new MPA(1, "Test"))
				.build();

		filmStorage.addFilm(film1);
		filmStorage.addFilm(film2);
		filmStorage.addFilm(film3);

		List<Film> filmList = filmStorage.getAllFilms();

		assertEquals(filmList.size(), 3);
		assertEquals(filmList.get(0).getId(), 1);
		assertEquals(filmList.get(1).getId(), 2);
		assertEquals(filmList.get(2).getId(), 3);
		assertNotNull(filmList);
	}

	@Test
	void shouldAddNewFilm() {
		Film film = Film.builder()
				.name("film1")
				.description("test")
				.releaseDate(LocalDate.of(1988, 6, 12))
				.rating("test")
				.duration(210)
				.mpa(new MPA(1, "Test"))
				.build();

		Film filmCopy = Film.builder()
				.id(1L)
				.name("film1")
				.description("test")
				.releaseDate(LocalDate.of(1988, 6, 12))
				.rating("test")
				.duration(210)
				.mpa(new MPA(1, "Test"))
				.build();

		filmStorage.addFilm(film);

		assertEquals(filmStorage.getAllFilms().size(), 1);
	}

	@Test
	void shouldUpdateFilm() {
		Film film = Film.builder()
				.name("film")
				.description("test")
				.releaseDate(LocalDate.of(1988, 6, 12))
				.rating("test")
				.duration(210)
				.mpa(new MPA(1, "Test"))
				.build();

		Film updatedFilm = Film.builder()
				.id(1L)
				.name("updatedFilm")
				.description("updated")
				.releaseDate(LocalDate.of(1970, 8, 22))
				.rating("test")
				.duration(180)
				.mpa(new MPA(1, "Test"))
				.build();

		filmStorage.addFilm(film);

		filmStorage.updateFilmById(updatedFilm);

		assertEquals(filmStorage.getFilmById(1L).get().getName(), updatedFilm.getName());
		assertEquals(filmStorage.getFilmById(1L).get().getDescription(), updatedFilm.getDescription());
		assertEquals(filmStorage.getFilmById(1L).get().getReleaseDate(), updatedFilm.getReleaseDate());
		assertEquals(filmStorage.getFilmById(1L).get().getRating(), updatedFilm.getRating());
		assertEquals(filmStorage.getFilmById(1L).get().getDuration(), updatedFilm.getDuration());
		assertNotEquals(filmStorage.getFilmById(1L), film);
	}

	@Test
	void shouldDeleteFilm() {
		Film film = Film.builder()
				.id(1L)
				.name("film")
				.description("test")
				.releaseDate(LocalDate.of(1988, 6, 12))
				.rating("test")
				.duration(210)
				.mpa(new MPA(1, "Test"))
				.build();

		filmStorage.addFilm(film);

		assertEquals(filmStorage.getAllFilms().size(), 1);

		filmStorage.deleteFilmById(1L);

		assertEquals(filmStorage.getAllFilms().size(), 0);
		assertEquals(filmStorage.getFilmById(1L), Optional.empty());
	}

}

package ru.yandex.practicum.filmorate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.controllers.UserValidationController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmorateApplication.class, args);
		Logger log = LoggerFactory.getLogger(UserValidationController.class);
//
//		User user0 = User.builder()
//				.id(1L)
//				.email("user123@mail.ru")
//				.login("user123")
//				.name("name")
//				.birthday(LocalDate.of(2023, 7, 30))
//				.build();
//
//		User user1 = User.builder()
//				.id(2L)
//				.email("user123@mail.ru")
//				.login("user123")
//				.name("name")
//				.birthday(LocalDate.of(2023, 7, 30))
//				.build();
//
//		User user2 = User.builder()
//				.id(3L)
//				.email("user123@mail.ru")
//				.login("user123")
//				.name("name")
//				.birthday(LocalDate.of(2023, 7, 30))
//				.build();
//
//		User user3 = User.builder()
//				.id(4L)
//				.email("user123@mail.ru")
//				.login("user123")
//				.name("name")
//				.birthday(LocalDate.of(2023, 7, 30))
//				.build();
//
//		User user4 = User.builder()
//				.id(5L)
//				.email("user123@mail.ru")
//				.login("user123")
//				.name("name")
//				.birthday(LocalDate.of(2023, 7, 30))
//				.build();
//
//		User user5 = User.builder()
//				.id(6L)
//				.email("user123@mail.ru")
//				.login("user123")
//				.name("name")
//				.birthday(LocalDate.of(2023, 7, 30))
//				.build();
//
//		Film film0 = Film.builder()
//				.id(1L)
//				.name("The Godfather")
//				.description("Don Vito Corleone" +
//						", head of a mafia family, decides to hand over " +
//						"his empire to his youngest son Michael. " +
//						"However, his decision unintentionally puts the " +
//						"lives of his loved ones in grave danger.")
//				.releaseDate(LocalDate.of(1972, 3, 14))
//				.duration(153)
//				.build();
//
//		Film film1 = Film.builder()
//				.id(2L)
//				.name("The Fly")
//				.description("Don Vito Corleone" +
//						", head of a mafia family, decides to hand over " +
//						"his empire to his youngest son Michael. " +
//						"However, his decision unintentionally puts the " +
//						"lives of his loved ones in grave danger.")
//				.releaseDate(LocalDate.of(1972, 3, 14))
//				.duration(153)
//				.build();
//
//		Film film2 = Film.builder()
//				.id(3L)
//				.name("The Spider Man")
//				.description("Don Vito Corleone" +
//						", head of a mafia family, decides to hand over " +
//						"his empire to his youngest son Michael. " +
//						"However, his decision unintentionally puts the " +
//						"lives of his loved ones in grave danger.")
//				.releaseDate(LocalDate.of(1972, 3, 14))
//				.duration(153)
//				.build();
//
//		Film film3 = Film.builder()
//				.id(4L)
//				.name("The Shining")
//				.description("Don Vito Corleone" +
//						", head of a mafia family, decides to hand over " +
//						"his empire to his youngest son Michael. " +
//						"However, his decision unintentionally puts the " +
//						"lives of his loved ones in grave danger.")
//				.releaseDate(LocalDate.of(1972, 3, 14))
//				.duration(153)
//				.build();
//
//		Film film4 = Film.builder()
//				.id(5L)
//				.name("The Taxi Driver")
//				.description("Don Vito Corleone" +
//						", head of a mafia family, decides to hand over " +
//						"his empire to his youngest son Michael. " +
//						"However, his decision unintentionally puts the " +
//						"lives of his loved ones in grave danger.")
//				.releaseDate(LocalDate.of(1972, 3, 14))
//				.duration(153)
//				.build();
//
//		FilmService filmService = new FilmService(new InMemoryFilmStorage());
//
//		filmService.addLikeToAFilm(film0, user0);
//		filmService.addLikeToAFilm(film0, user1);
//		filmService.addLikeToAFilm(film0, user2);
//		filmService.addLikeToAFilm(film0, user3);
//
//		filmService.addLikeToAFilm(film1, user0);
//
//		filmService.addLikeToAFilm(film2, user0);
//		filmService.addLikeToAFilm(film2, user1);
//		filmService.addLikeToAFilm(film2, user2);
//
//		filmService.addLikeToAFilm(film3, user0);
//		filmService.addLikeToAFilm(film3, user1);
//
//		filmService.addLikeToAFilm(film4, user0);
//		filmService.addLikeToAFilm(film4, user1);
//		filmService.addLikeToAFilm(film4, user2);
//		filmService.addLikeToAFilm(film4, user3);
//		filmService.addLikeToAFilm(film4, user4);
//		filmService.addLikeToAFilm(film4, user5);
//
//		Map<Long, Film> filmMap = new HashMap<>();
//		filmMap.put(film0.getId(), film0);
//		filmMap.put(film1.getId(), film1);
//		filmMap.put(film2.getId(), film2);
//		filmMap.put(film3.getId(), film3);
//		filmMap.put(film4.getId(), film4);
//
//		System.out.println(filmService.getTopTenMostPopularFilms(filmMap));


		/*User user = User.builder()
				.id(1L)
				.email("user123@mail.ru")
				.login("user123")
				.name("name")
				.birthday(LocalDate.of(2023, 7, 11))
				.build();

		User friend = User.builder()
				.id(2L)
				.email("user123@mail.ru")
				.login("user123")
				.name("name")
				.birthday(LocalDate.of(2023, 7, 12))
				.build();

		User friend2 = User.builder()
				.id(3L)
				.email("user123@mail.ru")
				.login("user123")
				.name("name")
				.birthday(LocalDate.of(2023, 7, 20))
				.build();

		UserService userService = new UserService(new InMemoryUserStorage());
		InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
		inMemoryUserStorage.addUser(user, log);
		inMemoryUserStorage.addUser(friend, log);
		inMemoryUserStorage.addUser(friend2, log);

		userService.addFriend(user.getId(), friend.getId());
		userService.addFriend(user.getId(), friend2.getId());
		userService.addFriend(friend.getId(), friend2.getId());
*/
//		System.out.println(user.getFriends());
//		System.out.println(friend.getFriends());
//
//
//		System.out.println(userService.getCommonFriendsList(user, friend));

		//System.out.println(userService.getUsersFriends(user.getId()));
	}

}

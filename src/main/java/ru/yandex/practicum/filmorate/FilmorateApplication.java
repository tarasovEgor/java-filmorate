package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmorateApplication.class, args);





//		User user = new User(1, "user@mail.ru", "name", "name",
//				LocalDate.of(1299, 12, 12));
	}

}

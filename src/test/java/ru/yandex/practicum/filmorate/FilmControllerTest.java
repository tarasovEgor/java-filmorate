package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class FilmControllerTest extends FilmTest {
    private FilmController controller;
    private Film film;

    @BeforeEach
    public void init() {
        controller = new FilmController();
        film = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();
    }

    @Test
    public void shouldReturnAllFilms() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();

        Map<Integer, Film> films = new HashMap<>();
        films.put(newFilm.getId(), newFilm);

        controller.createFilm(film);
        Map<Integer, Film> filmsCopy = controller.getAllFilms();

        assertEquals(films, filmsCopy);
        assertEquals(filmsCopy.size(), 1);
    }

    @Test
    public void shouldAddAFilm() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();

        controller.createFilm(newFilm);

        assertEquals(film, newFilm);
        assertEquals(controller.getAllFilms().get(1), film);
        assertEquals(controller.getAllFilms().size(), 1);
    }

    @Test
    public void shouldUpdateFilm() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();

        controller.createFilm(newFilm);

        Film updatedFilm = Film.builder()
                .id(1)
                .name("Forest Gump")
                .description("The movie Forrest Gump follows the life events" +
                        " of a man who shares the name as the title of the film.")
                .releaseDate(LocalDate.of(1994, 7, 6))
                .duration(142)
                .build();

        controller.updateFilm(updatedFilm);

        assertNotNull(controller.getAllFilms());
        assertEquals(controller.getAllFilms().get(1), updatedFilm);
        assertNotEquals(controller.getAllFilms().get(1), newFilm);
    }

    @Test
    public void shouldThrowValidationExceptionWhenFilmsNameIsEmpty() {
        Film newFilm = Film.builder()
                .id(1)
                .name("")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(newFilm)
        );

        assertEquals("Please, enter the film's name.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenFilmsDescriptionLengthIsGreaterThan200() {
        Film newFilm = Film.builder()
                .id(1)
                .name("Forest Gump")
                .description("\"The movie Forrest Gump follows the life events " +
                        " of a man who shares the name as the title of the film. " +
                        "Gump faces many tribulations throughout his life, but he " +
                        "never lets any of them interfere with his happiness.")
                .releaseDate(LocalDate.of(1994, 7, 6))
                .duration(142)
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(newFilm)
        );

        assertEquals("Film's description can't be more than 200 characters long", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenFilmsReleaseDateIsBefore1895_12_28() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(153)
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(newFilm)
        );

        assertEquals("Film's release date can't be earlier than 28-12-1895.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenFilmsDurationIsNegative() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(-153)
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createFilm(newFilm)
        );

        assertEquals("Film's duration can't be negative.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedFilmsNameIsEmpty() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();

        controller.createFilm(newFilm);

        Film updatedFilm = Film.builder()
                .id(1)
                .name("")
                .description("The movie Forrest Gump follows the life events" +
                        " of a man who shares the name as the title of the film.")
                .releaseDate(LocalDate.of(1994, 7, 6))
                .duration(142)
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateFilm(updatedFilm)
        );

        assertEquals("Please, enter the film's name.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedFilmsDescriptionIsGreaterThan200() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();

        controller.createFilm(newFilm);

        Film updatedFilm = Film.builder()
                .id(1)
                .name("Forest Gump")
                .description("\"The movie Forrest Gump follows the life events " +
                        " of a man who shares the name as the title of the film. " +
                        "Gump faces many tribulations throughout his life, but he " +
                        "never lets any of them interfere with his happiness.")
                .releaseDate(LocalDate.of(1994, 7, 6))
                .duration(142)
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateFilm(updatedFilm)
        );

        assertEquals("Film's description can't be more than 200 characters long", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedFilmsReleaseDateIsBefore1895_12_28() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();

        controller.createFilm(newFilm);

        Film updatedFilm = Film.builder()
                .id(1)
                .name("Forest Gump")
                .description("The movie Forrest Gump follows the life events" +
                        " of a man who shares the name as the title of the film.")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(142)
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateFilm(updatedFilm)
        );

        assertEquals("Film's release date can't be earlier than 28-12-1895.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedFilmsDurationIsNegative() {
        Film newFilm = Film.builder()
                .id(1)
                .name("The Godfather")
                .description("Don Vito Corleone" +
                        ", head of a mafia family, decides to hand over " +
                        "his empire to his youngest son Michael. " +
                        "However, his decision unintentionally puts the " +
                        "lives of his loved ones in grave danger.")
                .releaseDate(LocalDate.of(1972, 3, 14))
                .duration(153)
                .build();

        controller.createFilm(newFilm);

        Film updatedFilm = Film.builder()
                .id(1)
                .name("Forest Gump")
                .description("The movie Forrest Gump follows the life events" +
                        " of a man who shares the name as the title of the film.")
                .releaseDate(LocalDate.of(1994, 7, 6))
                .duration(-142)
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateFilm(updatedFilm)
        );

        assertEquals("Film's duration can't be negative.", exc.getMessage());
    }
}

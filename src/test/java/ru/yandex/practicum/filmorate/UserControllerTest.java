package ru.yandex.practicum.filmorate;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class UserControllerTest extends UserTest {
    private UserController controller;
    private User user;
    @BeforeEach
    public void init() {
        controller = new UserController();
        user = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();
    }

    @Test
    public void shouldReturnAllUsers() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        Map<Integer, User> users = new HashMap<>();
        users.put(newUser.getId(), newUser);

        controller.createUser(user);
        Map<Integer, User> usersCopy = controller.getAllUsers();

        assertEquals(users, usersCopy);
        assertEquals(usersCopy.size(), 1);
    }

    @Test
    public void shouldCreateUser() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(newUser);

        assertEquals(user, newUser);
        assertEquals(controller.getAllUsers().get(1), user);
        assertEquals(controller.getAllUsers().size(), 1);
    }

    @Test
    public void shouldUpdateUser() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(newUser);

        User updatedUser = User.builder()
                .id(1)
                .email("updated123@mail.ru")
                .login("updatedUser123")
                .name("newName")
                .birthday(LocalDate.of(1998, 8, 30))
                .build();

        controller.updateUser(updatedUser);

        assertNotNull(controller.getAllUsers());
        assertEquals(controller.getAllUsers().get(1), updatedUser);
        assertNotEquals(controller.getAllUsers().get(1), newUser);
    }

    @Test
    public void shouldThrowValidationExceptionWhenUsersEmailIsEmpty() {
        User newUser = User.builder()
                .id(1)
                .email("")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createUser(newUser)
        );

        assertEquals("Email field is rather empty or doesn't contain a '@' symbol.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenUsersEmailDoesNotContainAtSignSymbol() {
        User newUser = User.builder()
                .id(1)
                .email("user123mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        final  ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createUser(newUser)
        );

        assertEquals("Email field is rather empty or doesn't contain a '@' symbol.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenUsersLoginIsEmpty() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createUser(newUser)
        );

        assertEquals("Login field is rather empty or contains spaces.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenUsersLoginContainsSpaces() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user 123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createUser(newUser)
        );

        assertEquals("Login field is rather empty or contains spaces.", exc.getMessage());
    }

    @Test
    public void shouldThrowValidationExceptionWhenUsersBirthdayIsAfterTodaysDate() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(2023, 7, 30))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.createUser(newUser)
        );

        assertEquals("Birthday cannot be past today's date.", exc.getMessage());
    }

    @Test
    public void  shouldSetUsersNameToLoginWhenNameFieldIsEmpty() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(newUser);

        assertEquals(controller.getAllUsers().get(1).getName(),
                controller.getAllUsers().get(1).getLogin()
        );
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedUsersEmailIsEmpty() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(newUser);

        User updatedUser = User.builder()
                .id(1)
                .email("")
                .login("updatedUser123")
                .name("newName")
                .birthday(LocalDate.of(1998, 8, 30))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateUser(updatedUser)
        );

        assertEquals("Email field is rather empty or doesn't contain a '@' symbol.", exc.getMessage());
        assertEquals(controller.getAllUsers().get(1), newUser);
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedUsersEmailDoesNotContainAtSignSymbol() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(newUser);

        User updatedUser = User.builder()
                .id(1)
                .email("updatedUser123mail.ru")
                .login("updatedUser123")
                .name("newName")
                .birthday(LocalDate.of(1998, 8, 30))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateUser(updatedUser)
        );

        assertEquals("Email field is rather empty or doesn't contain a '@' symbol.", exc.getMessage());
        assertEquals(controller.getAllUsers().get(1), newUser);
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedUsersLoginIsEmpty() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(newUser);

        User updatedUser = User.builder()
                .id(1)
                .email("updatedUser123@mail.ru")
                .login("")
                .name("newName")
                .birthday(LocalDate.of(1998, 8, 30))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateUser(updatedUser)
        );

        assertEquals("Login field is rather empty or contains spaces.", exc.getMessage());
        assertEquals(controller.getAllUsers().get(1), newUser);
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedUsersLoginContainsSpaces() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(newUser);

        User updatedUser = User.builder()
                .id(1)
                .email("updatedUser123@mail.ru")
                .login("updatedUser 123")
                .name("newName")
                .birthday(LocalDate.of(1998, 8, 30))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateUser(updatedUser)
        );

        assertEquals("Login field is rather empty or contains spaces.", exc.getMessage());
        assertEquals(controller.getAllUsers().get(1), newUser);
    }

    @Test
    public void shouldThrowValidationExceptionWhenUpdatedUsersBirthdayIsAfterTodaysDate() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(newUser);

        User updatedUser = User.builder()
                .id(1)
                .email("updatedUser123@mail.ru")
                .login("updatedUser123")
                .name("newName")
                .birthday(LocalDate.of(2023, 8, 30))
                .build();

        final ValidationException exc = assertThrows(
                ValidationException.class,
                () -> controller.updateUser(updatedUser)
        );

        assertEquals("Birthday cannot be past today's date.", exc.getMessage());
        assertEquals(controller.getAllUsers().get(1), newUser);
    }

    @Test
    public void shouldSetUpdatedUsersNameToLoginWhenNameIsEmpty() {
        User newUser = User.builder()
                .id(1)
                .email("user123@mail.ru")
                .login("user123")
                .name("name")
                .birthday(LocalDate.of(1999, 5, 23))
                .build();

        controller.createUser(user);

        User updatedUser = User.builder()
                .id(1)
                .email("updated123@mail.ru")
                .login("updatedUser123")
                .name("")
                .birthday(LocalDate.of(1998, 8, 30))
                .build();

        controller.updateUser(updatedUser);

        assertEquals(controller.getAllUsers().get(1).getName()
                , controller.getAllUsers().get(1).getLogin());
    }
}



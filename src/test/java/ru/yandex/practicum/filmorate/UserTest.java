package ru.yandex.practicum.filmorate;

abstract class UserTest {
    //GET-METHOD
    public abstract void shouldReturnAllUsers();

    //POST-METHOD
    public abstract void shouldCreateUser();

    //PUT-METHOD
    public abstract void shouldUpdateUser();

    //POST-VALIDATION TESTS
    public abstract void shouldThrowValidationExceptionWhenUsersEmailIsEmpty();
    public abstract void shouldThrowValidationExceptionWhenUsersEmailDoesNotContainAtSignSymbol();
    public abstract void shouldThrowValidationExceptionWhenUsersLoginIsEmpty();
    public abstract void shouldThrowValidationExceptionWhenUsersLoginContainsSpaces();
    public abstract void shouldThrowValidationExceptionWhenUsersBirthdayIsAfterTodaysDate();
    public abstract void shouldSetUsersNameToLoginWhenNameFieldIsEmpty();

    //PUT-VALIDATION TEST
    public abstract void shouldThrowValidationExceptionWhenUpdatedUsersEmailIsEmpty();
    public abstract void shouldThrowValidationExceptionWhenUpdatedUsersEmailDoesNotContainAtSignSymbol();
    public abstract void shouldThrowValidationExceptionWhenUpdatedUsersLoginIsEmpty();
    public abstract void shouldThrowValidationExceptionWhenUpdatedUsersLoginContainsSpaces();
    public abstract void shouldThrowValidationExceptionWhenUpdatedUsersBirthdayIsAfterTodaysDate();
    public abstract void shouldSetUpdatedUsersNameToLoginWhenNameIsEmpty();
}

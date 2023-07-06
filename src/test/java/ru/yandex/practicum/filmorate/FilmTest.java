package ru.yandex.practicum.filmorate;

abstract class FilmTest {
    //GET-METHOD
    public abstract void shouldReturnAllFilms();

    //POST-METHOD
    public abstract void shouldAddAFilm();

    //PUT-METHOD
    public abstract void shouldUpdateFilm();

    //POST-VALIDATION TESTS
    public abstract void shouldThrowValidationExceptionWhenFilmsNameIsEmpty();
    public abstract void shouldThrowValidationExceptionWhenFilmsDescriptionLengthIsGreaterThan200();
    public abstract void shouldThrowValidationExceptionWhenFilmsReleaseDateIsBefore1895_12_28();
    public abstract void shouldThrowValidationExceptionWhenFilmsDurationIsNegative();

    //PUT-VALIDATION TESTS
    public abstract void shouldThrowValidationExceptionWhenUpdatedFilmsNameIsEmpty();
    public abstract void shouldThrowValidationExceptionWhenUpdatedFilmsDescriptionIsGreaterThan200();
    public abstract void shouldThrowValidationExceptionWhenUpdatedFilmsReleaseDateIsBefore1895_12_28();
    public abstract void shouldThrowValidationExceptionWhenUpdatedFilmsDurationIsNegative();

 }

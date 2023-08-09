package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class Film {
    private Long id;

    @Builder.Default
    private Set<Long> likes = new HashSet<>();
    @NotBlank
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
    private final Set<String> genres;
    private final String rating;
}

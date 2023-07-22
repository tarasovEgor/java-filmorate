package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
public class LikesController {
    private FilmService filmService;

    public LikesController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films/popular")
    public List<Film> getTenMostPopularFilms(@RequestParam(required = false) String count) {
        if (count == null) {
            return filmService.getTopTenMostPopularFilms(10L);
        } else {
            return filmService.getTopTenMostPopularFilms(Long.valueOf(count));
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Long addLikeToAFilm(@PathVariable String id, @PathVariable String userId) {
        return filmService.addLikeToAFilm(Long.valueOf(id), Long.valueOf(userId));
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public String removeLikeFromAFilm(@PathVariable String id, @PathVariable String userId) {
        return filmService.removeLikeFromAFilm(Long.valueOf(id), Long.valueOf(userId));
    }


}

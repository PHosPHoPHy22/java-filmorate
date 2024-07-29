package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<Film> findAll() {
        log.info("Получение списка фильмов");
        return filmService.findAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film findById(@PathVariable Integer filmId) {
        log.info("Получение фильма с ID {}", filmId);
        return filmService.findById(filmId);
    }

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        log.info("Создание нового фильма: {}", newFilm.toString());
        return filmService.createFilm(newFilm);
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("Обновление фильма с ID {}", newFilm.getId());
        return filmService.updateFilm(newFilm);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id,
                        @PathVariable Integer userId) {
        log.info("Фильму с ID {} поставлен лайк пользователем с ID {}", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable Integer id,
                           @PathVariable Integer userId) {
        log.info("С фильма с ID {} снят лайк пользователя с ID {}", id, userId);
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> bestFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        return filmService.bestFilms(count);
    }
}
package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private Map<Long, Film> films = new HashMap<>();

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        validationCheck(film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (film.getId() == null) {
            throw new NotFoundException("Id can not be null");
        }
        validationCheck(film);
        Film newFilm = films.get(film.getId());
        newFilm.setName(film.getName());
        newFilm.setDuration(film.getDuration());
        newFilm.setDescription(film.getDescription());
        newFilm.setReleaseDate(film.getReleaseDate());
        return newFilm;
    }

    public void validationCheck(Film film) {
        if (film.getName() == null || film.getName().trim().isBlank()) {
            throw new NotFoundException("Film name is null");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Description is longer than 200 symbols");
        }
        if (film.getReleaseDate().equals(LocalDate.of(1895, 12, 25)) ||
                film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 25))) {
            throw new ValidationException("The release date of the film should not be earlier 25.12.1895");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("The duration of the movie cannot be less than 0");
        }
    }

    public long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}

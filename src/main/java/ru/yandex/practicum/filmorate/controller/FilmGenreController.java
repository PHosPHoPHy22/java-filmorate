package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class FilmGenreController { // Исправленное название контроллера

    private final GenreService genreService; // Исправленное название сервиса

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable Long genreId) { // Убрана спецификация "id"
        return genreService.getGenreById(genreId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Genre> getAllGenres() { // Убрана спецификация типа
        return genreService.getAll();
    }

    // Обработка исключений (пример)

}
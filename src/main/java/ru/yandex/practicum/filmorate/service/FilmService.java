package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> findAllFilms() {
        return filmStorage.getAll();
    }

    public Film findById(Integer filmId) {
        return filmStorage.getById(filmId);
    }

    public Film createFilm(@RequestBody Film newFilm) {
        validateFilm(newFilm);
        return filmStorage.save(newFilm);
    }

    public Film updateFilm(@RequestBody Film newFilm) {
        validateFilm(newFilm);
        return filmStorage.update(newFilm);
    }


    public void addLike(Integer filmId, Integer userId) {
        if (filmStorage.getById(filmId) == null) {
            String message = "Фильм с id = " + filmId + " не найден";
            log.error(message);
            throw new NotFoundException(message);
        }
        if (userStorage.getById(userId) == null) {
            String message = "Пользователь с id = " + userId + " не найден";
            log.error(message);
            throw new NotFoundException(message);
        }
        if (filmStorage.getById(filmId).getLikes().contains(userId)) {
            String message = "Пользователь уже оценил этот фильм ранее";
            log.error(message);
            throw new ValidationException(message);
        }
        filmStorage.getById(filmId).getLikes().add(userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        if (filmStorage.getById(filmId) == null) {
            String message = "Фильм с id = " + filmId + " не найден";
            log.error(message);
            throw new NotFoundException(message);
        }
        if (userStorage.getById(userId) == null) {
            String message = "Пользователь с id = " + userId + " не найден";
            log.error(message);
            throw new NotFoundException(message);
        }
        if (!filmStorage.getById(filmId).getLikes().contains(userId)) {
            String message = "Пользователь еще не оценил этот фильм";
            log.error(message);
            throw new ValidationException(message);
        }
        filmStorage.getById(filmId).getLikes().remove(userId);
    }

    private void validateFilm(Film film) {
        if (film.getName() == null || film.getName().trim().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма не может быть длиннее 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 25))) {
            throw new ValidationException("Дата выхода фильма не может быть раньше 25.12.1895");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
    }

    public List bestFilms(int count) {
        return filmStorage.getAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
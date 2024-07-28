package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film getFilm(int id) {
        Film film = filmStorage.getFilm(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        return film;
    }


    public void likeFilm(int filmId, int userId) {
        Film film = getFilm(filmId);
        if (film.getLikes().contains(userId)) {
            throw new ValidationException("Пользователь уже поставил лайк");
        }
        film.getLikes().add(userId);
        filmStorage.updateFilm(film);
    }

    public void unlikeFilm(int filmId, int userId) {
        Film film = getFilm(filmId);
        if (!film.getLikes().contains(userId)) {
            throw new ValidationException("Пользователь не ставил лайк");
        }
        film.getLikes().remove((Integer) userId);
        filmStorage.updateFilm(film);
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> allFilms = filmStorage.getAllFilms();
        allFilms.sort((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()));
        return allFilms.subList(0, Math.min(count, allFilms.size()));
    }
}
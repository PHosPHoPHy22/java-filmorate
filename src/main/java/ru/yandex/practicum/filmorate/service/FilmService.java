package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;
import ru.yandex.practicum.filmorate.storage.mappers.FilmRowMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final JdbcTemplate jdbc;
    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;

    public Film addLike(long id, long userId) {
        if (filmDbStorage.findById(id) == null) {
            throw new NotFoundException("Нет фильма с id: " + id);
        }
        if (userDbStorage.findById(userId) == null) {
            throw new NotFoundException("Нет пользователя с id: " + userId);
        }
        String sql = "INSERT INTO films_likes(film_id, user_id) VALUES(?, ?)";
        jdbc.update(sql, id, userId);
        return filmDbStorage.findById(id);
    }

    public Film deleteLikes(long id, long userId) {
        if (filmDbStorage.findById(id) == null) {
            throw new NotFoundException("Нет фильма с id: " + id);
        }
        if (userDbStorage.findById(userId) == null) {
            throw new NotFoundException("Нет пользователя с id: " + userId);
        }
        String sql = "DELETE FROM films_likes WHERE film_id = ? AND user_id = ?";
        jdbc.update(sql, id, userId);
        return filmDbStorage.findById(id);
    }

    public Collection<Film> findAll() {
        return filmDbStorage.findAll();
    }

    public void delete(long id) {
        filmDbStorage.delete(id);
    }

    public Film update(Film film) {
        return filmDbStorage.update(film);
    }

    public Film create(Film film) {
        return filmDbStorage.create(film);
    }

    public Film getById(long id) {
        return filmDbStorage.findById(id);
    }

    public List<Film> getPopular(int count) {
        // Объединенный запрос для получения ID и данных фильмов
        String sql = "SELECT f.* FROM films f JOIN films_likes fl ON f.id = fl.film_id GROUP BY fl.film_id ORDER BY COUNT(fl.user_id) DESC LIMIT ?";
        // Получаем список популярных фильмов в одном запросе
        List<Film> popularFilms = jdbc.query(sql, new FilmRowMapper(), count);
        return popularFilms;
    }


}
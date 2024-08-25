package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    void delete(long id);

}
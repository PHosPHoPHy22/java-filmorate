package ru.yandex.practicum.filmorate.storage;


import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

interface GenreStorage {

    List<Genre> findAll();

    Genre findById(long id);
}
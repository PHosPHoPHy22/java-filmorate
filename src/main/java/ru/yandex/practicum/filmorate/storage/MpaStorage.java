package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

interface MpaStorage {

    List<Mpa> findAll();

    Mpa findById(long id);
}
package ru.yandex.practicum.filmorate.storage;

import java.util.Set;

public interface LikesStorage {
    Set<Long> getLikesByFilmId(Long filmId);
}
package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    @Qualifier("genreDbStorage")
    private final GenreDbStorage filmGenreStorage;


    @Override
    public Collection<Genre> getAll() {
        return filmGenreStorage.getAllGenres();
    }

    @Override
    public Genre getGenreById(Long genreId) {
        return filmGenreStorage
                .getGenreById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id = " + genreId + " is not in the database"));
    }
}
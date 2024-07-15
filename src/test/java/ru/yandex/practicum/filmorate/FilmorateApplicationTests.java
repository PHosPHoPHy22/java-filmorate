package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilmorateApplicationTests {

	private final FilmController filmController = new FilmController();

	@Test
	void getAllFilms() {
		filmController.addFilm(new Film(1L, "Film 1", "Description 1", LocalDate.of(2023, 1, 1), 120));
		filmController.addFilm(new Film(2L, "Film 2", "Description 2", LocalDate.of(2022, 12, 1), 100));
		Collection<Film> films = filmController.getAllFilms();
		assertEquals(2, films.size());
	}

	@Test
	void addFilm() {
		Film film = new Film(1L, "Film 1", "Description 1", LocalDate.of(2023, 1, 1), 120);
		Film addedFilm = filmController.addFilm(film);
		assertNotNull(addedFilm.getId());
		assertEquals("Film 1", addedFilm.getName());
		assertEquals(120, addedFilm.getDuration());
		assertEquals("Description 1", addedFilm.getDescription());
		assertEquals(LocalDate.of(2023, 1, 1), addedFilm.getReleaseDate());
	}

	@Test
	void addFilm_InvalidName() {
		Film film = new Film(null, null, "Description 1", LocalDate.of(2023, 1, 1), 120);
		assertThrows(NotFoundException.class, () -> filmController.addFilm(film));
	}


	@Test
	void update() {
		Film film = new Film(1L, "Film 1", "Description 1", LocalDate.of(2023, 1, 1), 120);
		filmController.addFilm(film);
		Film updatedFilm = new Film(1L, "Updated Film", "Updated Description", LocalDate.of(2023, 2, 1), 150);
		Film result = filmController.update(updatedFilm);
		assertEquals("Updated Film", result.getName());
		assertEquals(150, result.getDuration());
		assertEquals("Updated Description", result.getDescription());
		assertEquals(LocalDate.of(2023, 2, 1), result.getReleaseDate());
	}

	@Test
	void update_InvalidName() {
		Film film = new Film(1L, "Film 1", "Description 1", LocalDate.of(2023, 1, 1), 150);
		filmController.addFilm(film);
		Film updatedFilm = new Film(1L, null, "Updated Description", LocalDate.of(2023, 2, 1), 150);
		assertThrows(NotFoundException.class, () -> filmController.update(updatedFilm));
	}


	@Test
	void update_InvalidReleaseDate() {
		Film film = new Film(1L, "Film 1", "Description 1", LocalDate.of(2023, 1, 1), 120);
		filmController.addFilm(film);
		Film updatedFilm = new Film(1L, "Film 1", "Updated Description", LocalDate.of(1895, 12, 24), 150);
		assertThrows(ValidationException.class, () -> filmController.update(updatedFilm));
	}

	@Test
	void update_InvalidDuration() {
		Film film = new Film(1L, "Film 1", "Description 1", LocalDate.of(2023, 1, 1), 120);
		filmController.addFilm(film);
		Film updatedFilm = new Film(1L, "Film 1", "Updated Description", LocalDate.of(2023, 2, 1), -1);
		assertThrows(ValidationException.class, () -> filmController.update(updatedFilm));
	}

}
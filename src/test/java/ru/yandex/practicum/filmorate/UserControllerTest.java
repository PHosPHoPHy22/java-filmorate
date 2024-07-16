package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private final UserController userController = new UserController();

    @Test
    void getAllFilms() {
        userController.addUser(new User(1L, "User 1", "user1@example.com", "User 1", LocalDate.of(1990, 1, 1)));
        userController.addUser(new User(2L, "User 2", "user2@example.com", "User 2", LocalDate.of(1991, 2, 2)));
        Collection<User> users = userController.getAllFilms();
        assertEquals(2, users.size());
    }

    @Test
    void addUser() {
        User user = new User(1L, "User 1", "user1@example.com", "User 1", LocalDate.of(1990, 1, 1));
        User addedUser = userController.addUser(user);
        assertNotNull(addedUser.getId());
        assertEquals("User 1", addedUser.getLogin());
        assertEquals("user1@example.com", addedUser.getEmail());
        assertEquals("User 1", addedUser.getName());
        assertEquals(LocalDate.of(1990, 1, 1), addedUser.getBirthday());
    }


    @Test
    void addUser_InvalidEmail() {
        User user = new User(1L, "User 1", "invalidemail", "User 1", LocalDate.of(1990, 1, 1));
        assertThrows(ValidationException.class, () -> userController.addUser(user));
    }


    @Test
    void addUser_InvalidBirthday() {
        User user = new User(1L, "User 1", "user1@example.com", "User 1", LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.addUser(user));
    }

    @Test
    void update() {
        User user = new User(1L, "User 1", "user1@example.com", "User 1", LocalDate.of(1990, 1, 1));
        userController.addUser(user);
        User updatedUser = new User(1L, "Updated User", "updateduser@example.com", "Updated User", LocalDate.of(1991, 2, 2));
        User result = userController.update(updatedUser);
        assertEquals("Updated User", result.getLogin());
        assertEquals("updateduser@example.com", result.getEmail());
        assertEquals("Updated User", result.getName());
        assertEquals(LocalDate.of(1991, 2, 2), result.getBirthday());
    }

    @Test
    void update_InvalidEmail() {
        User user = new User(1L, "User 1", "user1@example.com", "User 1", LocalDate.of(1990, 1, 1));
        userController.addUser(user);
        User updatedUser = new User(1L, "Updated User", "invalidemail", "Updated User", LocalDate.of(1991, 2, 2));
        assertThrows(ValidationException.class, () -> userController.update(updatedUser));
    }


    @Test
    void update_InvalidBirthday() {
        User user = new User(1L, "User 1", "user1@example.com", "User 1", LocalDate.of(1990, 1, 1));
        userController.addUser(user);
        User updatedUser = new User(1L, "Updated User", "updateduser@example.com", "Updated User", LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.update(updatedUser));
    }

    @Test
    void getNextId() {
        User user1 = new User(1L, "User 1", "user1@example.com", "User 1", LocalDate.of(1990, 1, 1));
        userController.addUser(user1);
        User user2 = new User(2L, "User 2", "user2@example.com", "User 2", LocalDate.of(1991, 2, 2));
        userController.addUser(user2);
        assertEquals(3L, userController.getNextId());
    }
}
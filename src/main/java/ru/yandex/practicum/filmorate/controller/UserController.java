package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getAllFilms() {
        return users.values();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        validationCheck(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Добавили пользователя");
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (user.getId() == null) {
            throw new NotFoundException("Id can not be null");
        }
        validationCheck(user);
        User newUser = users.get(user.getId());
        newUser.setBirthday(user.getBirthday());
        newUser.setLogin(user.getLogin());
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        return newUser;
    }

    public void validationCheck(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("The mail should not be empty");
        }
        if (!(user.getEmail().contains("@"))) {
            throw new ValidationException("The mail must contain the symbol @");
        }
        if (user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new ValidationException("The login cannot be empty and contain spaces");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("The date of birth was entered incorrectly");
        }

    }

    public long getNextId() {

        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
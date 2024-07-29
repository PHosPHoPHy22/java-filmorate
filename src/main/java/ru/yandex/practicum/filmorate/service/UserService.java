package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> findAllUsers() {
        return userStorage.findAll();
    }

    public User findById(Integer userId) {

        return userStorage.getById(userId);
    }

    public User createUser(@RequestBody User newUser) {
        return userStorage.save(newUser);
    }

    public User updateUser(@RequestBody User newUser) {
        return userStorage.update(newUser);
    }


    public void addToFriends(Integer firstId, Integer secondId) {
        if (userStorage.getById(firstId).getFriends().contains(secondId)) {
            String message = "Пользователи уже дружат";
            log.error(message);
            throw new ValidationException(message);
        }
        userStorage.getById(firstId).getFriends().add(secondId);
        userStorage.getById(secondId).getFriends().add(firstId);
    }

    public void removeFromFriends(Integer firstId, Integer secondId) {
        userStorage.getById(firstId).getFriends().remove(secondId);
        userStorage.getById(secondId).getFriends().remove(firstId);
    }

    public List<User> findFriends(Integer id) {
        return userStorage.getById(id).getFriends().stream()
                .map(userStorage::getById)
                .toList();
    }

    public List<User> findMutualFriends(Integer firstId, Integer secondId) {
        return userStorage.getById(firstId).getFriends().stream()
                .filter(id -> userStorage.getById(secondId).getFriends().contains(id))
                .map(userStorage::getById)
                .toList();
    }
}
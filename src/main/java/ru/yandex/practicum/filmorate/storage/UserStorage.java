package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User getUser(int id);
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(int id);
}
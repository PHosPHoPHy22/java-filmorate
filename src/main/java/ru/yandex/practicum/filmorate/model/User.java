package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private Integer id;
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

}
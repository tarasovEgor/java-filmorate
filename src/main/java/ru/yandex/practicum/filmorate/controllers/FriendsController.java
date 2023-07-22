package ru.yandex.practicum.filmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.IncorrectPathVariableException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
public class FriendsController {
    private UserService userService;

    public FriendsController(UserService userService) {
        this.userService = userService;
    }

    private final Logger log = LoggerFactory.getLogger(UserValidationController.class);

    @GetMapping("/users/{id}/friends")
    public List<User> getUsersFriends(@PathVariable String id) {

        return userService.getUsersFriends(Long.valueOf(id));
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<Long> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getCommonFriends(Long.valueOf(id), Long.valueOf(otherId));
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public List<Long> addANewFriend(@PathVariable String id, @PathVariable String friendId) {
        if (Integer.parseInt(id) < 0 || Integer.parseInt(friendId) < 0) {
            throw new IncorrectPathVariableException("User's or friend's ID is negative");
        }
        return userService.addFriend(Long.valueOf(id), Long.valueOf(friendId));
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable String id, @PathVariable String friendId) {
        return userService.deleteFriend(Long.valueOf(id), Long.valueOf(friendId));
    }


}

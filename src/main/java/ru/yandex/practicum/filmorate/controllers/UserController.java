package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.IncorrectPathVariableException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        if (userService.getUserById(Long.valueOf(id)).isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        } else {
            return userService.getUserById(Long.valueOf(id));
        }
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
//        if (userService.getAllUsers().contains(user)) {
//            userService.addUserWithExistingId(user);
//        } else if (user.getId() == null) {
//            userService.addUserWithNoId(user);
//        } else if (user.getName() == null) {
//            userService.addUserWithNoName(user);
//        } else {
//            userService.addUser(user);
//        }
//        return user;
        return userService.addUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        userService.updateUserById(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public Long deleteUserById(@PathVariable String id) {
        return userService.deleteUserById(Long.valueOf(id));
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getUsersFriends(@PathVariable String id) {
        return userService.getUsersFriends(Long.valueOf(id));
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        return userService.getCommonFriends(Long.valueOf(id), Long.valueOf(otherId));
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public List<Long> addANewFriend(@PathVariable String id, @PathVariable String friendId) {
        if (Integer.parseInt(id) < 0 || Integer.parseInt(friendId) < 0) {
            throw new IncorrectPathVariableException("User's or friend's ID is negative.");
        }
        return userService.addFriend(Long.valueOf(id), Long.valueOf(friendId));
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable String id, @PathVariable String friendId) {
        return userService.deleteFriend(Long.valueOf(id), Long.valueOf(friendId));
    }
}

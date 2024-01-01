package org.mapmark.web;

import org.mapmark.dto.UserDTO;
import org.mapmark.model.User;
import org.mapmark.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/registration")
    public ResponseEntity<User> createUser(@Validated @RequestBody UserDTO user) {
        User newUser = userService.createNewUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

//    @PostMapping("/update")
//    public ResponseEntity<User> updateUser(@Validated @RequestBody UserDTO user) {
//        User updatedUser = userService.updateUser(user);
//        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}

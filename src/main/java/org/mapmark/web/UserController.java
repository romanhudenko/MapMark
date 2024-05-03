package org.mapmark.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.mapmark.dto.LoginDTO;
import org.mapmark.dto.UserDTO;
import org.mapmark.model.User;
import org.mapmark.security.service.UserDetailsImpl;
import org.mapmark.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<User> createUser(@Validated @RequestBody UserDTO user) {
        User newUser = userService.createNewUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/status")
    public String userPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) return "user not authorized";
        return userDetails.getId() + " " + userDetails.getUsername();
    }


}

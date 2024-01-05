package org.mapmark.web;

import org.mapmark.model.User;
import org.mapmark.security.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class MainController {


    @GetMapping("/log")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ResponseEntity<User> logout() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/")
    public String userPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) return "user not authorized";
        return userDetails.getId() + " " + userDetails.getUsername();
    }



}

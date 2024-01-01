package org.mapmark.web;

import org.mapmark.security.service.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/")
    public String startPage() {
        return "hello im start page";
    }

    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {


        String userdata = userDetails.getUsername() + " "
                + userDetails.getId() +
                " IM HERE:hello im USER page";


        return userdata;
    }

    @GetMapping("/admin")
    public String adminPage() {
        System.out.println("IM HERE:hello im ADMIN page");
        return "hello im ADMIN page";
    }

    @GetMapping("/moderator")
    public String moderPage() {
        System.out.println("IM HERE:hello im MODERATOR page");
        return "hello im MODERATOR page";
    }

}

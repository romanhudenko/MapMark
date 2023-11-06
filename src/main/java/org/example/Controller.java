package org.example;


import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping()
    public String startPage() {
        return "hello im start page";
    }
}



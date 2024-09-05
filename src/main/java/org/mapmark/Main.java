package org.mapmark;

import org.springframework.context.annotation.PropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@PropertySource("classpath:/application.properties")
@SpringBootApplication
public class        Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
package ru.solomka.profile.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.solomka.profile")
public class MgProfileService {
    public static void main(String[] args) {
        SpringApplication.run(MgProfileService.class, args);
    }
}
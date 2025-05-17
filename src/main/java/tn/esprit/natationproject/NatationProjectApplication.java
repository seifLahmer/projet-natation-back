package tn.esprit.natationproject;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class NatationProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(NatationProjectApplication.class, args);
    }


}
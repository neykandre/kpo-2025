package hse.studying.zoo;

import hse.studying.zoo.services.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a main class for the zoo application.
 * It provides a console menu where the user can interact with the application.
 * The application is a Spring Boot application.
 */
@SpringBootApplication
public class ZooApplication {

    public static void main(String[] args) {
        var appContext = SpringApplication.run(ZooApplication.class, args);

        ConsoleMenu menu = appContext.getBean(ConsoleMenu.class);
        menu.start();
    }
}
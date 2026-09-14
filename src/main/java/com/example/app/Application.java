package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The starter creates the ORMTemplate from the DataSource, registers every repository interface as
 * a bean, bridges Storm's transactions with Spring's, and validates every entity against the live
 * schema at startup, after Flyway has migrated it.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

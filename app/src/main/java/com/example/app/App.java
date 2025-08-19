package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Aplicación principal para el desafío de 30 días de refactoring y debugging.
 *
 * <p>Esta aplicación sirve como base para todos los ejercicios diarios, implementando arquitectura
 * DDD con Spring Boot.
 */
@SpringBootApplication
@EnableCaching
@EnableRetry
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}

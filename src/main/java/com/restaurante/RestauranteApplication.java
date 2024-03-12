package com.restaurante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Restaurante.
 * Esta clase inicia la aplicación Spring Boot.
 */
@SpringBootApplication
public class RestauranteApplication {

    /**
     * Método principal para iniciar la aplicación Restaurante.
     * @param args Argumentos de línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        SpringApplication.run(RestauranteApplication.class, args);
    }

}

package com.restaurante.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración de la aplicación Restaurante.
 * Esta clase define la configuración de la aplicación, como la creación de beans.
 */
@Configuration
public class AplicationConfig {

    /**
     * Método que crea un bean ModelMapper para la aplicación.
     * @return Instancia de ModelMapper que se puede utilizar en la aplicación para mapeo de objetos.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

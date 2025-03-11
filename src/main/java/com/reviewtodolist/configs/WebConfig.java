package com.reviewtodolist.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// Habilitando arquivo de configuração para iniciar junto com a API
// É o mesmo que usar o application.properties
@Configuration

// Ativa a configuração completa do Spring MVC e desativa o padrão do Boot.
// Agora eu que gerencio as configurações e não o Spring de forma automática.
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry){

        // Permite CORS para todas as rotas
        registry.addMapping("/**");

        /*
            Adicionalmente:

                 .allowedOrigins("*") // Permite requisições de qualquer origem - localhost ou qualquer domínio
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*"); // Todos os headers permitidos
         */
    }

}

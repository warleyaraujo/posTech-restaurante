package com.fiap.restaurante.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("https://fiap-restaurante.onrender.com");
        devServer.setDescription("Server OnRender.com");

        Server prodServer = new Server();
        prodServer.setUrl("https://localhost:8080");
        prodServer.setDescription("Server de produção");

        Contact contact = new Contact();
        contact.setEmail("ricardo.peres2013@gmail.com");
        contact.setName("Ricardo");

        Info info = new Info()
                .title("Tech Challenge Fase III")
                .version("1.0")
                .contact(contact)
                .description("Documentação dos endpoints da fase III - Restaurantes");


        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}

package tn.esprit.natationproject.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(infoAPI());
    }

    public Info infoAPI() {
        return new Info().title("Projet Natation")
                .description("Seif Lahmer")
                .contact(contactAPI());
    }

    public Contact contactAPI() {
        Contact contact = new Contact().name("contact US")
                .email("saiflahmar1008@gmail.com");
        return contact;
    }

    @Bean
    public GroupedOpenApi competitionPublicApi() {
        return GroupedOpenApi.builder()
                .group("Competition Public Api")
                .pathsToMatch("/api/competitions/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi resultatPublicApi() {
        return GroupedOpenApi.builder()
                .group("Resultat Public Api")
                .pathsToMatch("/api/resultats/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi piscinePublicApi() {
        return GroupedOpenApi.builder()
                .group("Piscine Public Api")
                .pathsToMatch("/api/piscines/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi centrePublicApi() {
        return GroupedOpenApi.builder()
                .group("Centre Public Api")
                .pathsToMatch("/api/centres/**")
                .pathsToExclude("**")
                .build();
    }
    @Bean
    public GroupedOpenApi allPublicApi() {
        return GroupedOpenApi.builder()
                .group("All Public Api")
                .pathsToMatch("/**")
                .build();
    }

}

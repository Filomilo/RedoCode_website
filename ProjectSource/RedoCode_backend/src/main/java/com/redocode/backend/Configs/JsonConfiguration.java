package com.redocode.backend.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

@Configuration
public class JsonConfiguration {
    @Bean
    public com.fasterxml.jackson.databind.Module hibernateModule() {
        return new Hibernate5JakartaModule();
    }

}

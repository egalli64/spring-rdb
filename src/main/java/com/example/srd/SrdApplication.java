/**
 * Introduction to Spring - Relational DB
 * 
 * https://github.com/egalli64/spring-rdb
 */
package com.example.srd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.srd.m2.s4.CrudRegionRepository;

@SpringBootApplication
public class SrdApplication {
    private static final Logger log = LogManager.getLogger(SrdApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SrdApplication.class, args);
    }

    @Bean
    protected CommandLineRunner demo(CrudRegionRepository repository) {
        log.trace("Generating a bean that logs all regions (auto-executed at startup)");
        return _ -> repository.findAll().forEach(region -> log.info(region.toString()));
    }
}

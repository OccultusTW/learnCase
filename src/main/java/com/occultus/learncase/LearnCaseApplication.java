package com.occultus.learncase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.occultus.learncase.threads.unsafe.ThreadUnsafeExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootApplication
@EnableCaching
public class LearnCaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnCaseApplication.class, args);
    }
}

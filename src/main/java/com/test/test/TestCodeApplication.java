package com.test.test;

import com.test.test.service.EventLogService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.IOException;

@SpringBootApplication
@Slf4j
public class TestCodeApplication implements CommandLineRunner {

    @Autowired
    EventLogService eventLogService;

    public static void main(String[] args) {
        SpringApplication.run(TestCodeApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        eventLogService.parseAndFlagTheLogs(args[0]);
    }

}

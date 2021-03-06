package com.filatov.calc;

import com.filatov.calc.config.AppPropertyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppPropertyConfiguration.class)
@Slf4j
public class CalcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CalcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Hello from calc application!");
	}
}

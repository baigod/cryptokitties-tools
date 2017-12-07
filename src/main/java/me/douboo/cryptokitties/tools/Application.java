package me.douboo.cryptokitties.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import me.douboo.springboot.spring.jtt.annotation.EnableSpringJtt;

@SpringBootApplication
@EnableSpringJtt
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

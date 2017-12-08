package me.douboo.cryptokitties.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import me.douboo.springboot.spring.jtt.annotation.EnableSpringJtt;

@SpringBootApplication
@EnableSpringJtt
@EnableScheduling
public class Application {

	@Bean
	public TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("ck-poolScheduler");
		scheduler.setPoolSize(10);
		return scheduler;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

package com.taskmanager.task;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@OpenAPIDefinition(
		info = @Info(
				title = "Task Management System API",
				version = "1.0.0",
				description = "API documentation for Task Management System"
		),
		servers = {
				@Server(
						url = "http://localhost:8080",
						description = "Local development server"
				)
		}
)
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
		System.out.println("This is Task Manager");
	}

}

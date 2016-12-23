package com.rw.examples.spring_data_neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@ComponentScan
@SpringBootApplication
@EnableNeo4jRepositories
public class Application {
	

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}

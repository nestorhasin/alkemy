package org.alkemy.challenge.java;

import org.alkemy.challenge.java.repositories.DAOs.DatabaseJDBC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaApplication {

	public static void main(String[] args) {
		DatabaseJDBC.createDatabase();
		SpringApplication.run(JavaApplication.class, args);
	}

}

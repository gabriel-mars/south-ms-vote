package com.gabriel.martins.vote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MsVoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVoteApplication.class, args);
	}

}

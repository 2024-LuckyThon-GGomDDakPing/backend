package com.GGomDDakPing.QnLove.QnLove;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class QnLoveApplication {

	public static void main(String[] args) {
		SpringApplication.run(QnLoveApplication.class, args);
	}

}

package ru.evolenta.orders_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OrdersServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersServerApplication.class, args);
	}

}

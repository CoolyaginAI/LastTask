package ru.evolenta.productList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductListApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductListApplication.class, args);
	}

}

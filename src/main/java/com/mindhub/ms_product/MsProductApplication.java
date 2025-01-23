package com.mindhub.ms_product;

import com.mindhub.ms_product.repositories.ProductRepository;
import com.mindhub.ms_product.models.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProductApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(ProductRepository productRepository) {
		return args -> {
			Product product = new Product("Martillo", "Cabo de madera", 10.5, 17);
			Product product1 = new Product("Tornillo", "Para madera", 5.1, 199);

			productRepository.save(product);
			productRepository.save(product1);
		};
	}
}

package com.baayel.products;

import com.baayel.products.dao.ProductDao;
import com.baayel.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class ProductsApplication {

	private ProductDao productDao;

	@Autowired
	public ProductsApplication(ProductDao productDao) {
		this.productDao = productDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {


			System.out.println("Insert products in DB:");
			Stream.of("Ordinateur portable", "Tv Oled", "Smarthone", "Iphone").forEach( p -> {
				int random=new Random().nextInt(10)+1; //nextInt((max - min) + 1) + min;[min,max]
				int price = 90 * random;
				Product product = new Product();
				product.setImage("default.jpg");
				product.setPrice(price );
				product.setPurchasingPrice(price + random);
				product.setName(p);
				productDao.save(product);
			});

			System.out.println("======LIST OF PRODUCTS ========== ");
			productDao.findAll().forEach(System.out::println);


		};
	}

}

package com.baayel.products.services;

import com.baayel.products.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {

    Product saveProduct(Product product, MultipartFile file) ;

    List<Product> findAll();

    Optional<Product> findById(Long id );

    void delete(Long id);


    List<Product> findByPriceGreaterThan(int price);
}

package com.baayel.products.web.controller;


import com.baayel.products.model.Product;
import com.baayel.products.services.ProductServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {


    private final ProductServiceInterface productServiceInterface;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController( ProductServiceInterface productServiceInterface) {
        this.productServiceInterface = productServiceInterface;
    }


    @GetMapping
    public List<Product> index( ) {
        return productServiceInterface.findAll();
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable("id") Long id )  {
        Optional<Product> product=productServiceInterface.findById(id);
        if ( product.isEmpty() )
            throw new ProductNotFoundException("Product " + id + " not found.");
        return product.get();

    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> saveProduct(@Valid  Product product, @RequestParam("file") MultipartFile file ){

        Product productAdded =  productServiceInterface.saveProduct( product,file );


        if( productAdded == null )
            return ResponseEntity.noContent().build();
        logger.info("======product was successfully uploaded ===");


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created( location ).body(productAdded);
    }

    @DeleteMapping (value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        if(productServiceInterface.findById(id).isEmpty())
            throw new ProductNotFoundException("Product " + id + " not found.");
        productServiceInterface.delete(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@Valid  Product product, @PathVariable Long id, @RequestParam("file") MultipartFile file ) {
        if(productServiceInterface.findById(id).isEmpty())
            throw new ProductNotFoundException("Product " + id + " not found.");
        Product productAdded =  productServiceInterface.saveProduct( product,file );

        return productAdded;
    }



    @GetMapping(value = "/price/{price}")
    public List<Product> getProductsPriceGt(@PathVariable int price) {
        return productServiceInterface.findByPriceGreaterThan(price);
    }

}

package com.baayel.products.services;

import com.baayel.products.model.Product;
import com.baayel.products.dao.ProductDao;
import com.baayel.products.storage.StorageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductServiceInterface {


    private ProductDao productDao;
    private StorageServiceInterface storageServiceInterface;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, StorageServiceInterface storageServiceInterface) {
        this.productDao = productDao;
        this.storageServiceInterface = storageServiceInterface;
    }

    @Override
    public Product saveProduct(Product product, MultipartFile file) {
       String filename= storageServiceInterface.store(file);
        product.setImage(filename);
        productDao.save(product);

        return product;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productDao.findById( id);
    }

    @Override
    public void delete(Long id) {
    productDao.deleteById( id);
    }



    @Override
    public List<Product> findByPriceGreaterThan(int price) {
        return productDao.findByPriceGreaterThan( price );
    }
}

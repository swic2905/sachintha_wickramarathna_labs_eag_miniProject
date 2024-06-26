package com.sysco.product.services;

import com.sysco.product.entities.Product;
import com.sysco.product.exceptions.ErrorCode;
import com.sysco.product.exceptions.ProductNotFoundException;
import com.sysco.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long productId){
        return productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(ErrorCode.ERROR_PRODUCT_NOT_FOUND + productId));
    }

    public Product getProductByProductName(String productName){
        return productRepository.findByProductName(productName);
    }

    public Product udpateProduct(Product product, Long productId){
        Product existingProduct = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(ErrorCode.ERROR_PRODUCT_NOT_FOUND + productId));
        if(existingProduct != null){
            existingProduct.setProductName(product.getProductName());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setQty(product.getQty());
            existingProduct.setPhotoUrl(product.getPhotoUrl());
        }
        productRepository.save(existingProduct);
        return existingProduct;
    }

    public boolean deleteProduct(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(ErrorCode.ERROR_PRODUCT_NOT_FOUND + productId));
        if(product != null)
            productRepository.deleteById(productId);
        else
            return false;
        return true;
    }
}

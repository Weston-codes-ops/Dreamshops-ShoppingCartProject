package com.dailycodework.dream_shops.interfaces;

import com.dailycodework.dream_shops.model.Product;

import java.util.List;

public interface ProductInterface {
    //To add products to database
    Product addproduct(Product product);
    Product getProductById(Long id);
    //Delete the product
    void deleteProductById(Long id);
    // Update the product
    void updateProductById(Product product, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List <Product> getProductsByCategoryAndBrand(String category, String brand);
    List <Product> getProductsByName(String name);
    List <Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}

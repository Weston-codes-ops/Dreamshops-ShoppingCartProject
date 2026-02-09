package com.dailycodework.dream_shops.interfaces;

import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;

import java.util.List;

public interface ProductInterface {
    //To add products to database
    Product addproduct(AddProductRequest request);
    Product getProductById(Long id);
    //Delete the product
    void deleteProductById(Long id);
    // Update the product
    Product updateProductById(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List <Product> getProductsByCategoryAndBrand(String category, String brand);
    List <Product> getProductsByName(String name);
    List <Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}

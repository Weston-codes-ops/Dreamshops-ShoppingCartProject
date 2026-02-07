package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
List<Product> findByCategoryName(String category);
List <Product> findByBrand(String brand);
List <Product> findByCategoryAndBrand(String category,String brand);
}

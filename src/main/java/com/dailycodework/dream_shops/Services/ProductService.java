package com.dailycodework.dream_shops.Services;

import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.interfaces.ProductInterface;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.CategoryRepository;
import com.dailycodework.dream_shops.repository.ProductRepository;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//Lombok annotation for constructor injection
@RequiredArgsConstructor
public class ProductService implements ProductInterface {
    //Dependency used to inject should be final
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addproduct(AddProductRequest request) {
        //Check if category is found in database
        //If yes, set it as the new product in category, otherwise
        // Save it as a new category then set it as the new product in
        //new category

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
        .orElseGet(() -> {
            Category newCategory = new Category();
            return categoryRepository.save(newCategory);
        });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Product createProduct(AddProductRequest request, Category category) {
         return new Product(
                 request.getName(),
                 request.getBrand(),
                 request.getPrice(),
                 request.getInventory(),
                 request.getDescription(),
                 category
         );
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ResourceNotFoundException("Product not found");
        });
    }

    @Override
    public Product updateProductById(ProductUpdateRequest request, Long productId) {
    return productRepository.findById(productId)
            .map(existingProduct ->updateExistingProduct(existingProduct,request))
            .map(productRepository::save)
            .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct , ProductUpdateRequest request){
    existingProduct.setName(request.getName());
    existingProduct.setBrand(request.getBrand());
    existingProduct.setPrice(request.getPrice());
    existingProduct.setInventory(request.getInventory());
    existingProduct.setDescription(request.getDescription());

    Category category = categoryRepository.findByName(request.getCategory().getName());
    existingProduct.setCategory(category);
    return existingProduct;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}

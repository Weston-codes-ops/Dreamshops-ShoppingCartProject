package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.interfaces.ProductInterface;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;
import com.dailycodework.dream_shops.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class productController {
private ProductInterface productInterface;

public ResponseEntity<ApiResponse> getAllProducts() {
List<Product> products = productInterface.getAllProducts();
return ResponseEntity.ok(new ApiResponse("success", products));
}

@GetMapping("product/{productId}/product")
public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") Long id) {
    try {
        Product product = productInterface.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("success", product));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
}


@PostMapping("/add")
public ResponseEntity<ApiResponse> createProduct(@RequestBody AddProductRequest product) {
    try {
        Product theProduct = productInterface.addproduct(product);
        return ResponseEntity.ok(new ApiResponse("Add product success", theProduct));
    } catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
}

@PutMapping("/product/{productId}/update")
public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
    try {
        Product theProduct = productInterface.updateProductById(request, productId);
        return ResponseEntity.ok(new ApiResponse("Update product success", theProduct));
    } catch (ResourceNotFoundException e) {
        return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
}


@DeleteMapping("/product/{productId}/delete")
public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
    try {
        productInterface.deleteProductById(productId);
        return ResponseEntity.ok(new ApiResponse("Product deleted", productId));
    } catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }


}

@GetMapping("products/by/brand-and-name")
public ResponseEntity<ApiResponse> getProductByBrandandName(@RequestParam String brandName, @RequestParam String productName) {
    try {
        List<Product> products = productInterface.getProductsByBrandAndName(brandName, productName);
       if(products.isEmpty()) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, productName));
       }
       return ResponseEntity.ok(new ApiResponse("success", products));
    }catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }

}


    @GetMapping("products/by/category-and-name")
    public ResponseEntity<ApiResponse> getProductsByCategoryandName(@RequestParam String category, @PathVariable String brand) {
        try {
            List<Product> products = productInterface.getProductsByBrandAndName(category, brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, products));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/by-name")
public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
    try {
        List<Product> products = productInterface.getProductsByName(name);
        if(products.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, products));
        }
        return ResponseEntity.ok(new ApiResponse("success", products));
    }catch (Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
}

@GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand) {
        try {
            List<Product> products = productInterface.getProductsByBrand(brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, products));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/product/by-category")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productInterface.getProductsByCategory(category);
            if(products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(null, products));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        }catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }





}

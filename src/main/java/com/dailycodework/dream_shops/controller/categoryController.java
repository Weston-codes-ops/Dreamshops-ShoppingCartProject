package com.dailycodework.dream_shops.controller;


import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.interfaces.CategoryInterface;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class categoryController {

    private final CategoryInterface categoryInterface;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryInterface.getCategoryList();
            return ResponseEntity.ok(new ApiResponse("Found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }


    @PostMapping("category/{id}/add")
    public ResponseEntity <ApiResponse> addCategory(@RequestParam Category name) {
        try {
            Category theCategory = categoryInterface.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Added", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));

        }

    }

    @GetMapping("/categories/{id}/category")
    public ResponseEntity <ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Category theCategory = categoryInterface.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/categories/{name}/category")
    public ResponseEntity <ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category theCategory = categoryInterface.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }



    @DeleteMapping("/categories/{id}/delete")
    public ResponseEntity <ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
             categoryInterface.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/categories/{id}/update")
    public ResponseEntity <ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryInterface.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Update success", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}

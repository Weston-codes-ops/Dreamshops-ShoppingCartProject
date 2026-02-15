package com.dailycodework.dream_shops.interfaces;

import com.dailycodework.dream_shops.model.Category;

import java.util.List;

public interface CategoryInterface {
Category getCategoryById(Long id);
Category getCategoryByName(String name);
List<Category> getCategoryList();
Category addCategory(Category category);
Category updateCategory(Category category, Long id);
void deleteCategoryById(Long id);



}

package com.example.capstone1.Service;


import com.example.capstone1.Model.Category;
import com.example.capstone1.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public boolean updateCategory(Integer categoryId, Category category){
        Category oldCategory = categoryRepository.getById(categoryId);

        if (oldCategory != null) {
            oldCategory.setName(category.getName());
            categoryRepository.save(oldCategory);
            return true;
        }
        return false;
    }

    public boolean deleteCategory(int categoryId){
        Category category = categoryRepository.getById(categoryId);
        if (category != null) {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    public int checkCategoryId(int categoryId){
        for (Category category : getAllCategories()) {
            if (category.getId() == categoryId) {
                return 1;
            }
        }
        return 0;
    }
}
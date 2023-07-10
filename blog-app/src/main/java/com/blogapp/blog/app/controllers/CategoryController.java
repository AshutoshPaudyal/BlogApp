package com.blogapp.blog.app.controllers;

import com.blogapp.blog.app.payload.ApiResponse;
import com.blogapp.blog.app.payload.CategoryDto;
import com.blogapp.blog.app.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //post
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return  new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    //put
    @PutMapping("{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable("categoryId") Integer categoryId){
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,categoryId);
        return  ResponseEntity.ok(updatedCategory);
    }
    //delete
    @DeleteMapping("{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId){

         categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(new ApiResponse("Category has been deleted successfully",true),HttpStatus.OK);

    }

    //getById
    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryId){

        return  ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
    //getAll
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){

        return  ResponseEntity.ok(categoryService.getAllCategories());
    }
}

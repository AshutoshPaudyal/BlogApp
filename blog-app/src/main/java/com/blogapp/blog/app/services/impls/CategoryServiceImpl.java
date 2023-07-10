package com.blogapp.blog.app.services.impls;


import com.blogapp.blog.app.entities.Category;
import com.blogapp.blog.app.exception.ResourceNotFound;
import com.blogapp.blog.app.exception.SameResourceFound;
import com.blogapp.blog.app.payload.CategoryDto;
import com.blogapp.blog.app.repositories.CategoryRepo;
import com.blogapp.blog.app.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper modelMapper;
    private final CategoryRepo categoryRepo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = dtoToCategory(categoryDto);
        Optional<Category> category1 = categoryRepo.findCategoriesByCategoryTitle(category.getCategoryTitle());

        if(category1.isPresent()){
            throw new SameResourceFound("Title of same name has already been added");
        }
        Category savedCategory = categoryRepo.save(category);
        return categoryToDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","category id",categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = categoryRepo.save(category);

        return categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","category id",categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","category id",categoryId));
        return categoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories= categoryRepo.findAll();
        List<CategoryDto> categoryDtos =
                categories.stream().map(category -> categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    public Category dtoToCategory(CategoryDto categoryDto){

        Category category = modelMapper.map(categoryDto,Category.class);
        return category;
    }

    public CategoryDto categoryToDto(Category category){
        CategoryDto categoryDto = modelMapper.map(category,CategoryDto.class);
        return categoryDto;
    }
}

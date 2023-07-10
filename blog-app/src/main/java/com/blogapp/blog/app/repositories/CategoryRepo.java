package com.blogapp.blog.app.repositories;

import com.blogapp.blog.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {

    Optional<Category> findCategoriesByCategoryTitle(String title);



}

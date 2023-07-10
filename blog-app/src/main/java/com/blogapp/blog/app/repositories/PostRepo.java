package com.blogapp.blog.app.repositories;

import com.blogapp.blog.app.entities.Category;
import com.blogapp.blog.app.entities.Post;
import com.blogapp.blog.app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    Page<Post> findByUserId(Integer userId, Pageable pageable);

    Page<Post> findByCategoryCategoryId(Integer categoryId, Pageable pageable);

    //like ko query implement garcha containing le
    List<Post> findByPostTitleContaining(String keyword);
}

package com.blogapp.blog.app.repositories;


import com.blogapp.blog.app.entities.Comment;
import com.blogapp.blog.app.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

    List<CommentDto> findByPostPostId(Integer postId);
}

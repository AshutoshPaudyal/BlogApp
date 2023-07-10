package com.blogapp.blog.app.services;

import com.blogapp.blog.app.entities.Category;
import com.blogapp.blog.app.entities.Post;
import com.blogapp.blog.app.payload.PostDto;
import com.blogapp.blog.app.payload.PostResponse;

import java.util.List;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto,Integer postId);

    //delete
    void deletePost(Integer postId);

    //get All
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get Post By id
    PostDto getPostById(Integer postId);

    //get All Post By Category
    PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize,
                                    String sortBy, String sortDir);

    //get All Posts By User
    PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize,
                                String sortBy, String sortDir);

    //search posts
    List<PostDto> searchPosts(String keyword);


}

package com.blogapp.blog.app.services;


import com.blogapp.blog.app.payload.CommentDto;

import java.util.List;
import java.util.Set;

public interface CommentService {

    //create comment
    CommentDto createComment(CommentDto commentDto, Integer postId);

    //update comment
    CommentDto updateDto(CommentDto commentDto,Integer commentId);

    //delete comment
    void deleteDto(Integer commentId);

    //get comment by id
    CommentDto getCommentById(Integer commentId);

    //get all comments
    List<CommentDto> getAllComments();

    //get comment by post
    Set<CommentDto> getCommentByPost(Integer postId);
}

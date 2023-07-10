package com.blogapp.blog.app.controllers;

import com.blogapp.blog.app.payload.ApiResponse;
import com.blogapp.blog.app.payload.CommentDto;
import com.blogapp.blog.app.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //create comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable("postId") Integer postId){
        CommentDto createdComment = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    //update comment
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable("commentId") Integer commentId){
        CommentDto updatedComment = commentService.updateDto(commentDto, commentId);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }

    //delete Comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
        commentService.deleteDto(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment has been succesfully deleted",true),HttpStatus.OK);
    }

    //get comment By id
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("commentId") Integer commentId){
        CommentDto commentById = commentService.getCommentById(commentId);
        return new ResponseEntity<>(commentById,HttpStatus.OK);
    }
    //get all comments
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> allComments = commentService.getAllComments();
        return new ResponseEntity<>(allComments,HttpStatus.OK);
    }

    //get comment By post Id
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<Set<CommentDto>> getPostByPostId(@PathVariable("postId") Integer postId){
        Set<CommentDto> commentByPost = commentService.getCommentByPost(postId);
        return new ResponseEntity<>(commentByPost,HttpStatus.OK);

    }

}

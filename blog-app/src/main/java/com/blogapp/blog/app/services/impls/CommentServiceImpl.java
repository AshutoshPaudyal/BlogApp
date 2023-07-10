package com.blogapp.blog.app.services.impls;

import com.blogapp.blog.app.entities.Comment;
import com.blogapp.blog.app.entities.Post;
import com.blogapp.blog.app.exception.ResourceNotFound;
import com.blogapp.blog.app.payload.CommentDto;
import com.blogapp.blog.app.repositories.CommentRepo;
import com.blogapp.blog.app.repositories.PostRepo;
import com.blogapp.blog.app.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;
    private final PostRepo postRepo;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFound("Post","post id",postId));
        Comment comment = dtoToComment(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return commentToDto(savedComment);
    }

    @Override
    public CommentDto updateDto(CommentDto commentDto, Integer commentId) {

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()->new ResourceNotFound("Comment","comment id",commentId));
        comment.setComment(commentDto.getComment());

        Comment updatedComment = commentRepo.save(comment);

        return commentToDto(updatedComment);
    }

    @Override
    public void deleteDto(Integer commentId) {

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()->new ResourceNotFound("Comment","comment id",commentId));
        commentRepo.delete(comment);
    }

    @Override
    public CommentDto getCommentById(Integer commentId) {

        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()->new ResourceNotFound("Comment","comment id",commentId));
        return commentToDto(comment);
    }

    @Override
    public List<CommentDto> getAllComments() {

        List<Comment> comments = commentRepo.findAll();
        List<CommentDto> commentDtos = comments.stream().map(comment -> commentToDto(comment)).collect(Collectors.toList());

        return commentDtos;
    }

    @Override
    public Set<CommentDto> getCommentByPost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFound("Post","post id", postId));
        Set<Comment> comments = post.getComments();
        Set<CommentDto> commentDtos = comments.stream().map(comment -> commentToDto(comment)).collect(Collectors.toSet());
        return commentDtos;
    }

    private Comment dtoToComment(CommentDto commentDto){
        return modelMapper.map(commentDto,Comment.class);
    }

    private CommentDto commentToDto(Comment comment){
        return modelMapper.map(comment,CommentDto.class);
    }
}

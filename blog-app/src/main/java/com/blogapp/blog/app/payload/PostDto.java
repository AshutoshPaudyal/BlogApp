package com.blogapp.blog.app.payload;


import com.blogapp.blog.app.entities.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class PostDto {

    private Integer postId;

    @NotEmpty
    @Size(min = 4,message = "Title should be minimum of 4 characters")
    private String postTitle;

    @NotEmpty
    @Size(min = 10,message = "Content should be minimum of 10 characters")
    private String content;

    private String imageName;

    private Date addedDate;

    @NotEmpty
    private CategoryDto category;


    @NotEmpty
    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();
}


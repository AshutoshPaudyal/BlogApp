package com.blogapp.blog.app.controllers;

import com.blogapp.blog.app.config.AppConstants;
import com.blogapp.blog.app.entities.Post;
import com.blogapp.blog.app.payload.ApiResponse;
import com.blogapp.blog.app.payload.PostDto;
import com.blogapp.blog.app.payload.PostResponse;
import com.blogapp.blog.app.services.FileService;
import com.blogapp.blog.app.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    @Value("${project.image}")
   private String path;

    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable("userId") Integer userId,
                                              @PathVariable("categoryId") Integer categoryId){
        PostDto createPost = postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    //get post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUserId(@PathVariable("userId") Integer userId,
                                                         @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)
                                                             Integer pageNumber,
                                                         @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)
                                                             Integer pageSize,
                                                         @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)
                                                            String sortBy,
                                                         @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false)
                                                            String sortDir){

        PostResponse postResponse = postService.getPostsByUser(userId,pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //get post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategoryId(@PathVariable("categoryId") Integer categoryId,
                                                             @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)
                                                                 Integer pageNumber,
                                                             @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)
                                                                 Integer pageSize,
                                                             @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)
                                                                String sortBy,
                                                             @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false)
                                                                String sortDir){

        PostResponse postResponse = postService.getPostsByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    // get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)
                                                         Integer pageNumber,
                                                    @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)
                                                     Integer pageSize,
                                                    @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)
                                                     String sortBy,
                                                    @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false)
                                                     String sortDir){

        return ResponseEntity.ok(postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir));
    }

    //get post by id
    @GetMapping("posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }
    //delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post has been deleted successfully!!",true)
                ,HttpStatus.OK);
    }

    //update post
    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("postId") Integer postId){

        PostDto updatePost = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }

    //search post
    @GetMapping("/posts/search/{postTitle}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("postTitle") String postTitle){

        List<PostDto> postDtos = postService.searchPosts(postTitle);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);

    }

   //post image upload
    @PostMapping("posts/image/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
                                               @PathVariable("postId") Integer postId) throws IOException {

        String imageName = fileService.uploadImage(path, image);

        PostDto postDto = postService.getPostById(postId);

        postDto.setImageName(imageName);

        postService.updatePost(postDto,postId);

        return new ResponseEntity<>(postDto,HttpStatus.OK);

    }

    //ServeImage
    @GetMapping("posts/image/{imageName}")
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}

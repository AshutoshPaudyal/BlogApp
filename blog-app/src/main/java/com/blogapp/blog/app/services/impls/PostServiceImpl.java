package com.blogapp.blog.app.services.impls;

import com.blogapp.blog.app.entities.Category;
import com.blogapp.blog.app.entities.Post;
import com.blogapp.blog.app.entities.User;
import com.blogapp.blog.app.exception.ResourceNotFound;
import com.blogapp.blog.app.payload.PostDto;
import com.blogapp.blog.app.payload.PostResponse;
import com.blogapp.blog.app.repositories.CategoryRepo;
import com.blogapp.blog.app.repositories.PostRepo;
import com.blogapp.blog.app.repositories.UserRepo;
import com.blogapp.blog.app.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFound("User","user id",userId));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFound("Category","category id",categoryId));

        Post post = dtoToPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = postRepo.save(post);
        return postToDto(newPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFound("Post","post id",postId));
        post.setPostTitle(postDto.getPostTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());


        Post updatedPost = postRepo.save(post);
        return postToDto(updatedPost);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFound("Post","post id",postId));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);

         Page<Post> pagePost = postRepo.findAll(pageable);

        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream().map(post -> postToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFound("Post","post id",postId));
        return postToDto(post);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize,
                                           String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFound("Category","category id",categoryId));


        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost = postRepo.findByCategoryCategoryId(categoryId,pageable);
        List<Post> posts1 = pagePost.getContent();
        List<PostDto> postDtos = posts1.stream().map(post -> postToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize,
                                       String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFound("User","user id",userId));

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost = postRepo.findByUserId(userId,pageable);
        List<Post> posts1 = pagePost.getContent();
        List<PostDto> postDtos = posts1.stream().map(post -> postToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;

    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByPostTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map(post -> postToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    public Post dtoToPost(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }

    public PostDto postToDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }


}

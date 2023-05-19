package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts?PageNo=1&PageSize=5&SortBy=title&SortDir=desc
    //this is query parameter that's why we used &. this is not path parameter
    // if we don't give any direction of sorting it will go in default sorting order that is asc order
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "PageNo", defaultValue = "0",required = false) int PageNo,
            @RequestParam(value = "PageSize", defaultValue = "5",required = false) int PageSize,
            @RequestParam(value = "SortBy", defaultValue = "id",required = false) String SortBy,
            @RequestParam(value = "SortDir", defaultValue = "asc",required = false) String SortDir
    ){

       PostResponse postResponse =  postService.getAllPosts(PageNo,PageSize,SortBy,SortDir);
       return postResponse;
    }

    //http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}") // there is no ? mark here. if there is ? mark in url then it is a query parameter it will read by @RequestParam so this is a path parameter  it will read by @PathVariable
     public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto =  postService.getPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
     }


    //http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
     public ResponseEntity<?> updatePost(
             @Valid
             @RequestBody PostDto postDto,
             @PathVariable("id") long id,
             BindingResult result
     ){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
       PostDto dto =  postService.updatePost(postDto,id);

       return new ResponseEntity<>(dto,HttpStatus.OK);
     }

    //http://localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
     public ResponseEntity<String> deletePost( @PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post is Deleted", HttpStatus.OK);
     }
}

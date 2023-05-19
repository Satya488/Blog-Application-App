package com.blog.service.impl;

import com.blog.entities.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;// this is not a builtin class of springBoot it is a java library that is being downloaded this constructor based injection been not work
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);// here the JSON Object will store in postDto
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());


        Post savedPost = postRepository.save(post);// here it will save the entity object in database and return the entity object
//        PostDto dto = new PostDto();
//        dto.setId(savedPost.getId());
//        dto.setTitle(SavedPost.getTitle());
//        dto.setDescription(savedPost.getDescription());
//        dto.setContent(savedPost.getContent());
        PostDto dto = mapToDto(savedPost);//we convert the return entity object to Dto
        return dto;
    }

    @Override
    public PostResponse getAllPosts(int PageNo, int PageSize, String SortBy, String SortDir) {
        Sort sort = SortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?//the ignore case is used to String comparison without taking the case consideration
                Sort.by(SortBy).ascending() : Sort.by(SortBy).descending();// inside we write Direction.ASC OR .DESC. it will consider the value as asc or desc order
                            // here we used ternary operator instead of writing if and else condition, if the condition1 ascending() is true it will sort the code in ascending order otherwise condition2 descending() will run the code in descending order
        PageRequest pageable = PageRequest.of(PageNo,PageSize,sort);// this will convert the string in to an object type sort
        Page<Post> content = postRepository.findAll(pageable);
        List<Post> posts = content.getContent();
        List<PostDto> dtos =  posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElements(content.getTotalElements());
        postResponse.setLast(content.isLast());

        return postResponse;
    }

    @Override

    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(// here if the record is found it will return the record and store in post object if the record is not found by id it will go to else part and showing the exception
                () -> new ResourceNotFoundException("Post Not Found with id:" + id)
        );
        PostDto postDto = mapToDto(post);// it will convert the post entity object to dto object and return back to controller.
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

         Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id: " + id)
        );

         post.setTitle(postDto.getTitle());
         post.setContent(postDto.getContent());
         post.setDescription(postDto.getDescription());

        Post updatedPost =  postRepository.save(post);

       return mapToDto(updatedPost);

    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id: " + id)
        );
        postRepository.deleteById(id);
    }

    Post mapToEntity(PostDto postDto){// when we give the Dto object it will convert that to entity object and
                                      // that method will return the entity object
        Post post = mapper.map(postDto, Post.class);
        /*Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/
        return post;
    }

    PostDto mapToDto(Post post){//here we convert the return entity object to Dto and return the Dto object
        PostDto dto = mapper.map(post, PostDto.class);
       /* PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());*/
        return dto;
    }
}

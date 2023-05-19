package com.blog.service.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDTO;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper mapper) {// Constructor based injection
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO saveComment(Long postId, CommentDTO commentDTO) {// DTO object can't be saved in database now we convert dto to entity
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id: "+postId)
        );

        Comment comment = mapToEntity(commentDTO);// here pass the dto object to the calling method
        comment.setPost(post);//what ever the comment we write that comment we write for the post so that we set post to comment entity
        Comment newComment = commentRepository.save(comment);// here we save the entity object in database and also return back the entity object

        return mapToDto(newComment);//here we pass the entity object to the calling method then we return dto to controller layer
    }

    @Override
    public List<CommentDTO> getCommentByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id: "+postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id:" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found With Id:" + commentId)
        );

        //here the comment is referring  to the comment table and the getPost() is foreign key of comment table we have post_id then the getId()
        //will give the id number of the post which is foreign key in comment table then post.getId() this will get the id from the post table
        // now we compare with the id in the comment table for the post foreign key and the id primary key present in post table are they basically not equal
        //if equal then it not give any exception if not equal then give this comment does not belong to post and this will give us blogAPI Exception.
        if(!comment.getPost().getId().equals(post.getId())){//here we compare the post_id present in comment table and the id present in post table does it match or not
            throw new BlogAPIException("Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With Id:" + postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found With Id:" + commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to post");
        }
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
//        comment.setBody(commentDTO.getBody());
        Comment updateComment = commentRepository.save(comment);
        return mapToDto(updateComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with Id:" + postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id:" + commentId)
        );

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belong to post");
        }

         commentRepository.deleteById(commentId);
    }

    Comment mapToEntity(CommentDTO commentDTO){// here we convert dto to entity and set the dto value in entity class object reference variable
        Comment comment = mapper.map(commentDTO, Comment.class);
        /*Comment comment = new Comment();
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());*/
        return comment;
    }
    CommentDTO mapToDto(Comment comment){// here we convert entity to dto and set the entity value in dto class object reference variable
        CommentDTO commentDto = mapper.map(comment, CommentDTO.class);
     /* CommentDTO commentDto = new CommentDTO();
      commentDto.setId(comment.getId());
      commentDto.setBody(comment.getBody());
      commentDto.setEmail(comment.getEmail());
      commentDto.setName(comment.getName());*/
      return commentDto;
    }
}

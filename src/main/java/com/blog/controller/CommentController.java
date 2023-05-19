package com.blog.controller;

import com.blog.payload.CommentDTO;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/1/comments

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(
             @PathVariable("postId") long postId,
             @RequestBody CommentDTO commentDTO
             ){
         CommentDTO dto = commentService.saveComment(postId, commentDTO);

         return new ResponseEntity<>(dto,HttpStatus.CREATED);
     }


     //http://localhost:8080/api/posts/1/comments
     @GetMapping("/posts/{postId}/comments")
     public List<CommentDTO> getCommentsByPostId(@PathVariable("postId") long postId){
         return commentService.getCommentByPostId(postId);

     }

    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{commentId}")
     public ResponseEntity<CommentDTO> getCommentById(
             @PathVariable("postId") long postId,
             @PathVariable("commentId") long commentId){
        CommentDTO dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/1/comments/1
    // through the url postId,commentId and the JSON object will received by controller layer

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            @RequestBody CommentDTO commentDTO// by the help of @RequestBody annotation The JSON object will convert into commentDto
   ){
      CommentDTO dto = commentService.updateComment(postId,commentId,commentDTO);
      return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/1/comments/1

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId
    ){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }

}

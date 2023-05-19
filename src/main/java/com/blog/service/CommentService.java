package com.blog.service;

import com.blog.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO saveComment(Long postId,CommentDTO commentDTO);

    List<CommentDTO> getCommentByPostId(long postId);

    CommentDTO getCommentById(long postId, long commentId);


    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);

    void deleteComment(long postId, long commentId);
}

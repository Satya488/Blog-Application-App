package com.blog.repositories;

import com.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
               List<Comment> findByPostId(long postId);//when we call this method by id springBoot will automatically search the record based on the id.
}

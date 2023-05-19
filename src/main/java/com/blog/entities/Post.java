package com.blog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts",
      uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
      )
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Title", nullable = false)
    private String title;
    @Column(name = "Description",nullable = false)
    private String description;
    @Column(name = "Content",nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)

    private Set<Comment> comments = new HashSet<>() ; // Here it is one to many one that is post not to be Set or List and many that is comment it is Set or List
}

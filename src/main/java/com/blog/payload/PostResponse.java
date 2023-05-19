package com.blog.payload;

import lombok.Data;

import java.util.List;
@Data
public class PostResponse {
    List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
}

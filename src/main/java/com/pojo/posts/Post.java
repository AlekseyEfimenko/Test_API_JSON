package com.pojo.posts;

import lombok.Data;

@Data
public class Post {
    private String title;
    private String body;
    private String userId;
    private Integer id;

    public Post(String title, String body, String userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}


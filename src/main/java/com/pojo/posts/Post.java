package com.pojo.posts;

import lombok.Getter;
import lombok.Setter;

public class Post {
    @Getter @Setter private String title;
    @Getter @Setter private String body;
    @Getter @Setter private String userId;
    @Getter @Setter private Integer id;

    public Post(String title, String body, String userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}


package com.pojo.posts;

public class Post {
    private final String title;
    private final String body;
    private final String userId;
    private Integer id;

    public Post(String title, String body, String userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getUserId() {
        return userId;
    }

    public Integer getId() {
        return id;
    }
}


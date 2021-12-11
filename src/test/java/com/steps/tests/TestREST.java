package com.steps.tests;

import com.steps.TestSteps;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class TestREST {
    private static final String GET_POSTS = "posts";
    private static final String GET_POST_99 = "posts/99";
    private static final String GET_POST_150 = "posts/150";
    private static final String GET_USERS = "users";
    private static final String GET_USERS_5 = "users/5";
    private static final int SUCCESS_STATUS_CODE = 200;
    private static final int NOT_FOUND_STATUS_CODE = 404;
    private static final int CREATED_STATUS_CODE = 201;
    private static final ContentType CONTENT_TYPE = ContentType.JSON;
    private static final String ID = "id";
    private static final String USER_ID = "userId";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String POST_99_ID = "99";
    private static final String POST_99_USERID = "10";
    private static final String POST_99_TITLE = "";
    private static final String POST_99_BODY = "";
    private static final String EMPTY_BODY = "{}";
    private final TestSteps steps = new TestSteps();


    @Test
    public void testGetAndPost() {
        steps.getPosts(GET_POSTS);
        steps.assertStatusCode(SUCCESS_STATUS_CODE);
        steps.assertListFormat(CONTENT_TYPE);
        steps.assertListSortedBy(ID);

        steps.getPosts(GET_POST_99);
        steps.assertStatusCode(SUCCESS_STATUS_CODE);
        steps.assertValueEquals(USER_ID, POST_99_USERID);
        steps.assertValueEquals(ID, POST_99_ID);
        steps.assertValueIsNotEmpty(TITLE, POST_99_TITLE);
        steps.assertValueIsNotEmpty(BODY, POST_99_BODY);

        steps.getPosts(GET_POST_150);
        steps.assertStatusCode(NOT_FOUND_STATUS_CODE);
        steps.assertBodyIsEmpty(EMPTY_BODY);

        steps.postPosts(GET_POSTS);
        steps.assertStatusCode(CREATED_STATUS_CODE);
        steps.assertValueIsValid(TITLE);
        steps.assertValueIsValid(BODY);
        steps.assertValueIsValid(USER_ID);
        steps.assertValueIsPresent(ID);

        steps.getPosts(GET_USERS);
        steps.assertStatusCode(SUCCESS_STATUS_CODE);
        steps.assertListFormat(CONTENT_TYPE);
        steps.assertUsersEqual();

        steps.getPosts(GET_USERS_5);
        steps.assertStatusCode(SUCCESS_STATUS_CODE);
        steps.assertUser5IsCorrect();
    }
}


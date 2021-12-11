package com.utils;

import com.pojo.posts.Post;
import com.pojo.users.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import java.util.List;

/**
 * Class, that provides the opportunity to make requests to API
 */
public class APIUtils {
    private static final Logger LOGGER = Logger.getLogger(APIUtils.class);
    private static final String BASE_PATH = Configurations.getInstance().getRequestURL();
    private static APIUtils instance;
    private Response response;

    private APIUtils() {}

    public static APIUtils getInstance() {
        if (instance == null) {
            instance = new APIUtils();
        }
        return instance;
    }

    /**
     * Get the status code of request:
     * 1xx - information;
     * 2xx - success;
     * 3xx - redirect;
     * 4xx - client error;
     * 5xx - server error
     * @return Status code
     */
    public int getStatusCode () {
        LOGGER.info("Get status code of request");
        return response.statusCode();
    }

    /**
     * Get the request from API by URL
     * @param target URL for getting request
     */
    public void getRequest(String target) {
        response = RestAssured.when().get(BASE_PATH + target);
    }

    /**
     * Checking if ContentType equals to given
     * @param format ContentType to check with
     */
    public void checkContentType(ContentType format) {
        LOGGER.info("Get content type of request");
        response.then().assertThat().contentType(format);
    }

    /**
     * Get the list of some value from API request
     * @param target The key to get list of values
     * @param <T> The type of value
     * @return List of values by key "target"
     */
    public <T extends Comparable<T>> List<T> getList(String target) {
        return response.jsonPath().getList(target);
    }

    /**
     * Getting value from the request by the key
     * @param key The key to get value of
     * @param <T> The type of the key and return value
     * @return The value by the key
     */
    public <T> T getValue(T key) {
        return response.jsonPath().get(key.toString());
    }

    /**
     * Get the request body from the API server
     * @return The String, that represents the request body
     */
    public String getBody() {
        return response.getBody().asString();
    }

    /**
     * Send data to the API server to create new resource
     * @param target URL to post request
     * @param post The Post object to be sent
     */
    public void postRequest(String target, Post post) {
        response = RestAssured.given().header("Content-Type", "application/json").body(post).post(BASE_PATH + target);
    }

    /**
     * Converting request from API server to Post object
     * @return Post object
     */
    public Post convertPostToPojo() {
        return response.as(Post.class);
    }

    /**
     * Converting request from API server to list of User objects
     * @return List of User objects
     */
    public List<User> getListOfUsers() {
        LOGGER.info("Get list of users");
        return response.jsonPath().getList("$", User.class);
    }

    /**
     * Converting request from API server to User object
     * @return User object
     */
    public User convertUserToPojo() {
        return response.as(User.class);
    }
}



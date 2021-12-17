package com.utils;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.List;

/**
 * Class, that provides the opportunity to make requests to API
 */
public class ApiUtils {
    private static final Logger LOGGER = Logger.getLogger(ApiUtils.class);
    private static final String BASE_PATH = Configurations.getInstance().getRequestURL();
    private static final String CONTENT_TYPE = "Content-Type";
    private static ApiUtils instance;
    private Response response;

    private ApiUtils() {}

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
            RestAssured.filters(new MyRequestFilter());
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
        return response.statusCode();
    }

    /**
     * Get the request from API by URL
     * @param target URL for getting request
     */
    public void getRequest(String target) {
        response = RestAssured.given().when()
                .get(BASE_PATH + target);
    }

    /**
     * Get the Content type of the request
     * @return String representation of Content type
     */
    public String getContentType() {
        LOGGER.info("Get content type of request");
        return response.contentType().split(";")[0];
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
     * @param obj T object to be sent
     */
    public <T> void postRequest(String target, T obj) {
        response = RestAssured.given().header(CONTENT_TYPE, ContentType.JSON).body(obj).post(BASE_PATH + target);
    }

    /**
     * Converting request from API server to T class object
     * @return T object
     */
    public <T> T convertRequestToPojo(Class<T> cl) {
        return response.as(cl);
    }

    /**
     * Converting request from API server to list of T objects
     * @return List of T objects
     */
    public <T> List<T> getListOfUsers(Class<T> cl) {
        LOGGER.info("Get list of users");
        return response.jsonPath().getList("$", cl);
    }

    static class MyRequestFilter implements Filter {

        private static final Logger LOGGER = Logger.getLogger(MyRequestFilter.class.getName());

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            Response response = ctx.next(requestSpec, responseSpec);
            if (requestSpec.getMethod().equals("GET")) {
                LOGGER.info("Getting request from " + requestSpec.getURI());
            } else if (requestSpec.getMethod().equals("POST")) {
                LOGGER.info("Post request to " + requestSpec.getURI());
            }
            LOGGER.info("Status code of request is: " + response.statusCode());
            if (response.statusCode() >= 400) {
                LOGGER.log(Level.ERROR, requestSpec.getURI() + " => " + response.getStatusLine());
            }
            return response;
        }
    }
}


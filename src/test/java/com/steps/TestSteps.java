package com.steps;

import static com.utils.MyAssert.myAssertTrue;
import static com.utils.MyAssert.myAssertEquals;
import static com.utils.MyAssert.myAssertNotEquals;
import static com.utils.MyAssert.myAssertNotNull;
import com.pojo.posts.Post;
import com.pojo.users.Address;
import com.pojo.users.Company;
import com.pojo.users.Geo;
import com.pojo.users.User;
import com.utils.ApiUtils;
import com.utils.Configurations;
import org.apache.log4j.Logger;
import java.util.List;

public class TestSteps {
    private static final Logger LOGGER = Logger.getLogger(TestSteps.class);

    public void getPosts(String target) {
        LOGGER.info("Get request from " + Configurations.getInstance().getRequestURL() + target);
        ApiUtils.getInstance().getRequest(target);
    }

    public void assertStatusCode(int statusCode) {
        int status = ApiUtils.getInstance().getStatusCode();
        myAssertEquals(status, statusCode);
    }

    public void assertListFormat(String format) {
        myAssertEquals(ApiUtils.getInstance().getContentType(), format);
    }

    public <T extends Comparable<T>> void assertListSortedBy(String target) {
        LOGGER.info("Get list of " + target);
        List<T> list = ApiUtils.getInstance().getList(target);
        LOGGER.info("Check, if list is sorted by " + target);
        myAssertTrue(Configurations.getInstance().isSorted(list));
    }

    public <T> void assertValueEquals(T key, String value) {
        LOGGER.info("Check if " + key + " is equals to " + value);
        myAssertEquals(ApiUtils.getInstance().getValue(key).toString(), value);
    }

    public <T> void assertValueIsNotEmpty(T key, String value) {
        LOGGER.info("Check if " + key + " is not empty");
        myAssertNotEquals(ApiUtils.getInstance().getValue(key).toString(), value);
    }

    public void assertBodyIsEmpty(String value) {
        LOGGER.info("Check if body request is empty");
        myAssertEquals(ApiUtils.getInstance().getBody(), value);
    }

    public void postPosts(String target, Post post) {
        LOGGER.info("Post request to " + Configurations.getInstance().getRequestURL() + target);
        ApiUtils.getInstance().postRequest(target, post);
    }

    public void assertValueIsValid(String key, Post post) {
        LOGGER.info("Check if value by key " + key + " is valid");
        Post requestedPost = getPostFromRequest();
        switch (key) {
            case "title" -> myAssertEquals(requestedPost.getTitle(), post.getTitle());
            case "body" -> myAssertEquals(requestedPost.getBody(), post.getBody());
            case "userId" -> myAssertEquals(requestedPost.getUserId(), post.getUserId());
            default -> LOGGER.error("Wrong key is entered");
        }
    }

    public void assertValueIsPresent(String key) {
        LOGGER.info("Check if key " + key + " is present in request");
        Post requestedPost = getPostFromRequest();
        myAssertNotNull(requestedPost.getId());
    }

    private Post getPostFromRequest() {
        return ApiUtils.getInstance().convertRequestToPojo(Post.class);
    }

    public void assertUsersEqual(User user) {
        List<User> users = getUsers();
        setUpUser(user);
        LOGGER.info("Check if user fields with id \"5\" have correct information");
        myAssertEquals(users.get(4), user);
    }

    public void assertUser5IsCorrect(User user) {
        User user5 = getUser();
        LOGGER.info("Check if user 5 fields have correct information");
        myAssertEquals(user, user5);
    }

    private void setUpUser(User user) {
        Geo geo = new Geo();
        Address address = new Address();
        Company company = new Company();

        geo.setLat("-31.8129");
        geo.setLng("62.5342");

        address.setStreet("Skiles Walks");
        address.setSuite("Suite 351");
        address.setCity("Roscoeview");
        address.setZipcode("33263");
        address.setGeo(geo);

        company.setName("Keebler LLC");
        company.setCatchPhrase("User-centric fault-tolerant solution");
        company.setBs("revolutionize end-to-end systems");

        user.setId("5");
        user.setName("Chelsey Dietrich");
        user.setUsername("Kamren");
        user.setEmail("Lucio_Hettinger@annie.ca");
        user.setAddress(address);
        user.setPhone("(254)954-1289");
        user.setWebsite("demarco.info");
        user.setCompany(company);
    }

    private User getUser() {
        return ApiUtils.getInstance().convertRequestToPojo(User.class);
    }

    private List<User> getUsers() {
        return ApiUtils.getInstance().getListOfUsers(User.class);
    }
}


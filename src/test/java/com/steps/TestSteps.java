package com.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNotEquals;
import com.pojo.posts.Post;
import com.pojo.users.Address;
import com.pojo.users.Company;
import com.pojo.users.Geo;
import com.pojo.users.User;
import com.utils.APIUtils;
import com.utils.Configurations;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import org.testng.Assert;
import java.util.List;

public class TestSteps {
    private static final Logger LOGGER = Logger.getLogger(TestSteps.class);
    private final User user = new User();
    private Post post;
    private Post requestedPost;
    private User user5;
    private List<User> users;

    public void getPosts(String target) {
        LOGGER.info("Get request from " + Configurations.getInstance().getRequestURL() + target);
        APIUtils.getInstance().getRequest(target);
    }

    public void assertStatusCode(int statusCode) {
        int status = 0;
        try {
            status = APIUtils.getInstance().getStatusCode();
            assertEquals(status, statusCode);
        } catch (AssertionError as) {
            LOGGER.error("Status code is " + status + ", expected status code is " + statusCode);
            throw new AssertionError();
        }
    }

    public void assertListFormat(ContentType format) {
        try {
            APIUtils.getInstance().checkContentType(format);
        } catch (AssertionError as) {
            LOGGER.error("Content type is not " + format.name());
            throw new AssertionError();
        }
    }

    public <T extends Comparable<T>> void assertListSortedBy(String target) {
        LOGGER.info("Get list of " + target);
        List<T> list = APIUtils.getInstance().getList(target);
        try {
            LOGGER.info("Check, if list is sorted by " + target);
            assertTrue(Configurations.getInstance().isSorted(list));
        } catch (AssertionError as) {
            LOGGER.error("List is not sorted");
            throw new AssertionError();
        }
    }

    public <T> void assertValueEquals(T key, String value) {
        LOGGER.info("Check if " + key + " is equals to " + value);
        try {
            assertEquals(APIUtils.getInstance().getValue(key).toString(), value);
        } catch (AssertionError as) {
            LOGGER.error("The value by key " + key + " is not " + value);
            throw new AssertionError();
        }
    }

    public <T> void assertValueIsNotEmpty(T key, String value) {
        LOGGER.info("Check if " + key + " is not empty");
        try {
            assertNotEquals(APIUtils.getInstance().getValue(key).toString(), value);
        } catch (AssertionError as) {
            LOGGER.error("The value by key " + key + " is not empty");
            throw new AssertionError();
        }
    }

    public void assertBodyIsEmpty(String value) {
        LOGGER.info("Check if body request is empty");
        try {
            assertEquals(APIUtils.getInstance().getBody(), value);
        } catch (AssertionError as) {
            LOGGER.error("Body request is not empty");
            throw new AssertionError();
        }
    }

    public void postPosts(String target) {
        post = new Post(Configurations.getInstance().getRandomString(), Configurations.getInstance().getRandomString(), "1");
        LOGGER.info("Post request to " + Configurations.getInstance().getRequestURL() + target);
        APIUtils.getInstance().postRequest(target, post);
    }

    public void assertValueIsValid(String key) {
        LOGGER.info("Check if value by key " + key + " is valid");
        requestedPost = APIUtils.getInstance().convertPostToPojo();
        switch (key) {
            case "title" -> assertEquals(requestedPost.getTitle(), post.getTitle());
            case "body" -> assertEquals(requestedPost.getBody(), post.getBody());
            case "userId" -> assertEquals(requestedPost.getUserId(), post.getUserId());
            default -> LOGGER.error("Wrong key is entered");
        }
    }

    public void assertValueIsPresent(String key) {
        LOGGER.info("Check if key " + key + " is present in request");
        Assert.assertNotNull(requestedPost.getId());
    }

    public void setUser() {
        user5 = APIUtils.getInstance().convertUserToPojo();
    }

    public void setUsers() {
        users = APIUtils.getInstance().getListOfUsers();
    }

    public void setUpUser() {
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

    public void assertUsersEqual() {
        setUsers();
        setUpUser();
        LOGGER.info("Check if user fields with id \"5\" have correct information");

        assertEquals(user.getId(), users.get(4).getId());
        assertEquals(user.getName(), users.get(4).getName());
        assertEquals(user.getUsername(), users.get(4).getUsername());
        assertEquals(user.getEmail(), users.get(4).getEmail());
        assertEquals(user.getAddress().getStreet(), users.get(4).getAddress().getStreet());
        assertEquals(user.getAddress().getSuite(), users.get(4).getAddress().getSuite());
        assertEquals(user.getAddress().getCity(), users.get(4).getAddress().getCity());
        assertEquals(user.getAddress().getZipcode(), users.get(4).getAddress().getZipcode());
        assertEquals(user.getAddress().getGeo().getLat(), users.get(4).getAddress().getGeo().getLat());
        assertEquals(user.getAddress().getGeo().getLng(), users.get(4).getAddress().getGeo().getLng());
        assertEquals(user.getPhone(), users.get(4).getPhone());
        assertEquals(user.getWebsite(), users.get(4).getWebsite());
        assertEquals(user.getCompany().getName(), users.get(4).getCompany().getName());
        assertEquals(user.getCompany().getCatchPhrase(), users.get(4).getCompany().getCatchPhrase());
        assertEquals(user.getCompany().getBs(), users.get(4).getCompany().getBs());
    }

    public void assertUser5IsCorrect() {
        setUser();
        LOGGER.info("Check if user 5 fields have correct information");

        assertEquals(user.toString(), user5.toString());
    }
}


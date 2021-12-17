package com.pojo.users;

import lombok.Data;
import java.util.Objects;

@Data
public class User {
    private String id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    @Override
    public String toString() {
        return String.format("User: %nid: %s%nname: %s%nusername: %s%nemail: %s%naddress: %s%nphone: %s%nwebsite: %s%ncompany: %s",
                id, name, username, email, address, phone, website, company);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name)
                && Objects.equals(username, user.username) && Objects.equals(email, user.email)
                && Objects.equals(address, user.address) && Objects.equals(phone, user.phone)
                && Objects.equals(website, user.website) && Objects.equals(company, user.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, address, phone, website, company);
    }
}


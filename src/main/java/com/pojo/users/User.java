package com.pojo.users;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

public class User {
    @Getter @Setter private String id;
    @Getter @Setter private String name;
    @Getter @Setter private String username;
    @Getter @Setter private String email;
    @Getter @Setter private Address address;
    @Getter @Setter private String phone;
    @Getter @Setter private String website;
    @Getter @Setter private Company company;

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
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(address, user.address) && Objects.equals(phone, user.phone) && Objects.equals(website, user.website) && Objects.equals(company, user.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, address, phone, website, company);
    }
}


package com.pojo.users;

import lombok.Data;
import java.util.Objects;

@Data
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo = new Geo();

    @Override
    public String toString() {
        return String.format("%n\tstreet: %s%n\tsuite: %s%n\tcity: %s%n\tzipcode: %s%n\tgeo: %s",
                street, suite, city, zipcode, geo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(suite, address.suite)
                && Objects.equals(city, address.city) && Objects.equals(zipcode, address.zipcode)
                && Objects.equals(geo, address.geo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, suite, city, zipcode, geo);
    }
}


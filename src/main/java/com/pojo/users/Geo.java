package com.pojo.users;

import lombok.Data;
import java.util.Objects;

@Data
public class Geo{
    private String lat;
    private String lng;

    @Override
    public String toString() {
        return String.format("%n\t\tlat: %s%n\t\tlng: %s",
                lat, lng);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geo geo = (Geo) o;
        return Objects.equals(lat, geo.lat) && Objects.equals(lng, geo.lng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng);
    }
}


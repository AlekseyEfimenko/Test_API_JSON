package com.pojo.users;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

public class Company {
    @Getter @Setter private String name;
    @Getter @Setter private String catchPhrase;
    @Getter @Setter private String bs;

    @Override
    public String toString() {
        return String.format("%n\tname: %s%n\tcatchPhrase: %s%n\tbs: %s", name, catchPhrase, bs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(catchPhrase, company.catchPhrase) && Objects.equals(bs, company.bs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, catchPhrase, bs);
    }
}


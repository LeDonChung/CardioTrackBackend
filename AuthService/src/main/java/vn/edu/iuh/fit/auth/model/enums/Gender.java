package vn.edu.iuh.fit.auth.model.enums;

import lombok.Getter;

public enum Gender {
    Male("Male"),
    Female("Female"),
    Other("Other");

    private final String value;

    Gender(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }
}

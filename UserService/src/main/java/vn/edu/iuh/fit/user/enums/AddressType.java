package vn.edu.iuh.fit.user.enums;

import lombok.Getter;

@Getter
public enum AddressType {
    HOME("HOME"),
    OTHER("OTHER");

    private final String value;

    AddressType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }


}

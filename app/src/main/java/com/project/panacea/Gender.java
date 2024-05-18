package com.project.panacea;

public enum Gender {
    MALE(0), FEMALE(1), OTHER(2);

    private final int value;

    private Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Gender fromInt(int value) {
        switch (value) {
            case 0:
                return MALE;
            case 1:
                return FEMALE;
            case 2:
                return OTHER;
            default:
                throw new IllegalArgumentException("Invalid gender value: " + value);
        }
    }
}


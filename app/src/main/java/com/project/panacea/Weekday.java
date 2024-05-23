package com.project.panacea;

public enum Weekday {
    SATURDAY(0), SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6);

    private final int value;

    private Weekday(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Weekday fromInt(int value) {
        switch (value) {
            case 0:
                return SATURDAY;
            case 1:
                return SUNDAY;
            case 2:
                return MONDAY;
            case 3:
                return TUESDAY;
            case 4:
                return WEDNESDAY;
            case 5:
                return THURSDAY;
            case 6:
                return FRIDAY;
            default:
                throw new IllegalArgumentException("Invalid weekday value: " + value);
        }
    }
}

package com.project.panacea;

import org.junit.Test;
import static org.junit.Assert.*;

public class GenderTest {

    @Test
    //done
    public void testGetValue() {
        assertEquals("MALE value should be 0", 0, Gender.MALE.getValue());
        assertEquals("FEMALE value should be 1", 1, Gender.FEMALE.getValue());
        assertEquals("OTHER value should be 2", 2, Gender.OTHER.getValue());
    }

    @Test
    //done
    public void testFromInt() {
        assertEquals("Value 0 should return MALE", Gender.MALE, Gender.fromInt(0));
        assertEquals("Value 1 should return FEMALE", Gender.FEMALE, Gender.fromInt(1));
        assertEquals("Value 2 should return OTHER", Gender.OTHER, Gender.fromInt(2));
    }

    @Test(expected = IllegalArgumentException.class)
    //done
    public void testFromIntWithInvalidValue() {
        Gender.fromInt(3); // This should throw an IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    //done
    public void testFromIntWithNegativeValue() {
        Gender.fromInt(-1); // This should throw an IllegalArgumentException
    }
}

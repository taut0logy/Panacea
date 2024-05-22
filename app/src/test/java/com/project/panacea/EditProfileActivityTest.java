package com.project.panacea;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class EditProfileActivityTest {

    private EditProfileActivity editProfileActivity;

    @Mock
    private AuthUtility mockAuthUtility;
    @Mock
    private UserUtility mockUserUtility;

    @Before
    public void setup() {
        // Create an instance of the activity
        editProfileActivity = new EditProfileActivity();

        // Set the mocked utilities
        mockAuthUtility = mock(AuthUtility.class);
        mockUserUtility = mock(UserUtility.class);
        editProfileActivity.setUtilities(mockAuthUtility, mockUserUtility);
    }

    @Test
    public void testValidateEmail() {
        assertTrue(editProfileActivity.validateEmail("test@example.com"));
        assertTrue(editProfileActivity.validateEmail("valid.email+tag@example.co.uk"));
        assertFalse(editProfileActivity.validateEmail("invalid-email"));
        assertFalse(editProfileActivity.validateEmail("invalid@.com"));
    }

    @Test
    public void testValidatePhoneNumber() {
        assertTrue(editProfileActivity.validatePhoneNumber("01234567890"));
        assertFalse(editProfileActivity.validatePhoneNumber("12345"));
        assertFalse(editProfileActivity.validatePhoneNumber("phone12345"));
        assertFalse(editProfileActivity.validatePhoneNumber("012345678901"));
    }

    @Test
    public void testValidateDateOfBirth() {
        assertTrue(editProfileActivity.validateDateOfBirth("01/01/2000"));
        assertFalse(editProfileActivity.validateDateOfBirth("01-01-2000"));
        assertFalse(editProfileActivity.validateDateOfBirth("2000/01/01"));
        assertFalse(editProfileActivity.validateDateOfBirth("01012000"));
    }

    @Test
    public void testValidateInput() {
        editProfileActivity.etName.setText("John Doe");
        editProfileActivity.etEmail.setText("john.doe@example.com");
        editProfileActivity.etPhoneNumber.setText("01234567890");
        editProfileActivity.etDateOfBirth.setText("01/01/2000");

        assertTrue(editProfileActivity.validateInput());

        editProfileActivity.etName.setText("");
        assertFalse(editProfileActivity.validateInput());

        editProfileActivity.etName.setText("John Doe");
        editProfileActivity.etEmail.setText("invalid-email");
        assertFalse(editProfileActivity.validateInput());

        editProfileActivity.etEmail.setText("john.doe@example.com");
        editProfileActivity.etPhoneNumber.setText("12345");
        assertFalse(editProfileActivity.validateInput());

        editProfileActivity.etPhoneNumber.setText("01234567890");
        editProfileActivity.etDateOfBirth.setText("01012000");
        assertFalse(editProfileActivity.validateInput());
    }
}

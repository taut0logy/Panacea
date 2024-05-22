package com.project.panacea;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EditProfileActivityTest {

    @Rule
    public ActivityScenarioRule<EditProfileActivity> activityRule =
            new ActivityScenarioRule<>(EditProfileActivity.class);

    @Test
    public void testProfileActivityDisplaysCorrectUserData() {
        // Check that the EditTexts and other UI elements are displayed correctly
        onView(withId(R.id.editEtName)).check(matches(isDisplayed()));
        onView(withId(R.id.editEtEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.editEtPhone)).check(matches(isDisplayed()));
        onView(withId(R.id.editEtDob)).check(matches(isDisplayed()));
        onView(withId(R.id.editRgGender)).check(matches(isDisplayed()));
        onView(withId(R.id.editAvatar)).check(matches(isDisplayed()));
        onView(withId(R.id.editBtnSave)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditingProfile() {
        // Enter name
        onView(withId(R.id.editEtName)).perform(replaceText("Jane Doe"));
        // Enter email
        onView(withId(R.id.editEtEmail)).perform(replaceText("jane.doe@example.com"));
        // Enter phone number
        onView(withId(R.id.editEtPhone)).perform(replaceText("09876543210"));

        // Open date picker and set date
        onView(withId(R.id.editEtDob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePickerDialog.class.getName())))
                .perform(PickerActions.setDate(2000, 1, 1));
        onView(withText("OK")).perform(click());

        // Select gender
        onView(withId(R.id.editRbFemale)).perform(click());

        // Check that the data entered is correct
        onView(withId(R.id.editEtName)).check(matches(withText("Jane Doe")));
        onView(withId(R.id.editEtEmail)).check(matches(withText("jane.doe@example.com")));
        onView(withId(R.id.editEtPhone)).check(matches(withText("09876543210")));
        onView(withId(R.id.editEtDob)).check(matches(withText("01/01/2000")));

        // Click save button
        onView(withId(R.id.editBtnSave)).perform(click());
    }

    @Test
    public void testAvatarUpload() {
        // Click on avatar to show popup
        onView(withId(R.id.editAvatar)).perform(click());
        // Simulate selecting camera option from the popup
        onView(withText("Camera")).perform(click());

        // Additional actions to simulate taking a picture can be added here

        // Check that the avatar image view is displayed
        onView(withId(R.id.editAvatar)).check(matches(isDisplayed()));
    }

    @Test
    public void testValidation() {
        // Enter invalid email
        onView(withId(R.id.editEtEmail)).perform(replaceText("invalid_email"));
        // Click save button
        onView(withId(R.id.editBtnSave)).perform(click());

        // Check for error message
        onView(withId(R.id.editEtEmail)).check(matches(withText("Invalid email.")));

        // Enter invalid phone number
        onView(withId(R.id.editEtPhone)).perform(replaceText("12345"));
        // Click save button
        onView(withId(R.id.editBtnSave)).perform(click());

        // Check for error message
        onView(withId(R.id.editEtPhone)).check(matches(withText("Invalid phone number.")));
    }
}

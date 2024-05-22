package com.project.panacea;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProfileActivityTest {

    @Rule
    public ActivityScenarioRule<ProfileActivity> activityRule =
            new ActivityScenarioRule<>(ProfileActivity.class);

    private CountingIdlingResource idlingResource = new CountingIdlingResource("DataLoader");

    @Before
    public void setUp() {
        // Register the IdlingResource
        IdlingRegistry.getInstance().register(idlingResource);

        // Simulate data loading by incrementing the idling resource counter
        idlingResource.increment();

        // Simulate a background data loading task
        new Thread(() -> {
            // Simulated data loading delay
            performDataLoading();
            // Decrement the idling resource counter when data loading is done
            idlingResource.decrement();
        }).start();
    }

    private void performDataLoading() {
        // Simulate data loading process
        try {
            Thread.sleep(2000); // Simulate a delay for data loading, replace with actual loading if needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Unregister the IdlingResource
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void testProfileActivityDisplaysCorrectUserData() {
        // Check that the user details are displayed correctly
        onView(withId(R.id.profileTvName)).check(matches(withText("John Doe")));
        onView(withId(R.id.profileTvEmail)).check(matches(withText("john.doe@example.com")));
        onView(withId(R.id.profileTvPhone)).check(matches(withText("1234567890")));
        onView(withId(R.id.profileTvDob)).check(matches(withText("01/01/1990"))); // Use the appropriate date format
        onView(withId(R.id.profileTvGender)).check(matches(withText("MALE")));
    }

    @Test
    public void testProfileAvatarIsDisplayed() {
        // Check that the profile avatar is displayed
        onView(withId(R.id.profileAvatar)).check(matches(isDisplayed()));
    }



    @Test
    public void testEmailFieldIsVisible() {
        // Check that the email field is visible
        onView(withId(R.id.profileTvEmail)).check(matches(isDisplayed()));
    }

    @Test
    public void testPhoneNumberFieldIsVisible() {
        // Check that the phone number field is visible
        onView(withId(R.id.profileTvPhone)).check(matches(isDisplayed()));
    }
}

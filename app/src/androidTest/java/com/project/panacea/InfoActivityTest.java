package com.project.panacea;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.project.panacea.R;

import org.junit.Rule;
import org.junit.Test;

public class InfoActivityTest {

    @Rule
    public ActivityScenarioRule<InfoActivity> activityRule = new ActivityScenarioRule<>(InfoActivity.class);

    @Test
    public void testCardViewVisibility() {
        // Check if CardViews are displayed
        Espresso.onView(ViewMatchers.withId(R.id.Dhaka)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.Khulna)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.Barisal)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.Chattagram)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    // Add more UI tests as needed
}


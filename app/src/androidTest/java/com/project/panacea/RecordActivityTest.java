package com.project.panacea;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertNotNull;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class RecordActivityTest {
    @Rule
    public ActivityScenarioRule<RecordActivity> activityScenarioRule = new ActivityScenarioRule<>(RecordActivity.class);

    @Test
    public void launchShowRecordsActivity() {
        Espresso.onView(withId(R.id.showRecords)).perform(click());
        assertNotNull(ActivityScenario.launch(ShowRecords.class));
    }
}

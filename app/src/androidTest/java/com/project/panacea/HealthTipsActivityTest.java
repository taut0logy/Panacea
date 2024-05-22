package com.project.panacea;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class HealthTipsActivityTest {
    @Rule
    public ActivityScenarioRule<HealthTipsActivity> activityScenarioRule = new ActivityScenarioRule<>(HealthTipsActivity.class);





    @Test
    public void backButtonPressed() {
        Espresso.onView(withId(R.id.back_button)).perform(click());
        assertNotNull(ActivityScenario.launch(HomeActivity.class));
    }


    @Test
    public void testLoadChildFragment() {
        try (ActivityScenario<HealthTipsActivity> scenario = ActivityScenario.launch(HealthTipsActivity.class)) {
            scenario.onActivity(activity -> {
                activity.loadFragment(new ChildFragment());

                Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                assertNotNull(fragment);

                if (fragment instanceof ChildFragment) {
                    ChildFragment childFragment = (ChildFragment) fragment;
                    // Verify the getFrag method
                    String fragName = childFragment.getFrag();
                    assertEquals("ChildFragment", fragName);
                }
            });
        }
    }

    @Test
    public void testLoadDiabeticFragment() {
        try (ActivityScenario<HealthTipsActivity> scenario = ActivityScenario.launch(HealthTipsActivity.class)) {
            scenario.onActivity(activity -> {
                activity.loadFragment(new DiabeticFragment());

                Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                assertNotNull(fragment);

                if (fragment instanceof DiabeticFragment) {
                    DiabeticFragment diabeticFragment = (DiabeticFragment) fragment;
                    // Verify the getFrag method
                    String fragName = diabeticFragment.getFrag();
                    assertEquals("DiabeticFragment", fragName);
                }
            });
        }
    }

    @Test
    public void testLoadElderlyFragment() {
        try (ActivityScenario<HealthTipsActivity> scenario = ActivityScenario.launch(HealthTipsActivity.class)) {
            scenario.onActivity(activity -> {
                activity.loadFragment(new ElderlyFragment());

                Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                assertNotNull(fragment);

                if (fragment instanceof ElderlyFragment) {
                    ElderlyFragment elderlyFragment = (ElderlyFragment) fragment;
                    // Verify the getFrag method
                    String fragName = elderlyFragment.getFrag();
                    assertEquals("ElderlyFragment", fragName);
                }
            });
        }
    }

    @Test
    public void testLoadHypertensionFragment() {
        try (ActivityScenario<HealthTipsActivity> scenario = ActivityScenario.launch(HealthTipsActivity.class)) {
            scenario.onActivity(activity -> {
                activity.loadFragment(new HypertensionFragment());

                Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                assertNotNull(fragment);

                if (fragment instanceof HypertensionFragment) {
                    HypertensionFragment hypertensionFragment = (HypertensionFragment) fragment;
                    // Verify the getFrag method
                    String fragName = hypertensionFragment.getFrag();
                    assertEquals("HypertensionFragment", fragName);
                }
            });
        }
    }


}

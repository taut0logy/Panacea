package com.project.panacea;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BMIActivityTest {

    @Rule
    public ActivityScenarioRule<BMIActivity> activityRule =
            new ActivityScenarioRule<>(BMIActivity.class);

    @Test
    public void bmibmrworking(){

        onView(withId(R.id.edKg)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.edFeet)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.edIns)).perform(typeText("7"), closeSoftKeyboard());
        onView(withId(R.id.edAge)).perform(typeText("25"), closeSoftKeyboard());
        onView(withId(R.id.cardBtn)).perform(click());
        onView(withId(R.id.bmiResult)).check(matches(withText("17.26")));
    }



    @Test
    public void wtNegativeValue(){
        onView(withId(R.id.edKg)).perform(typeText("-50"), closeSoftKeyboard());
        onView(withId(R.id.edFeet)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.edIns)).perform(typeText("7"), closeSoftKeyboard());
        onView(withId(R.id.edAge)).perform(typeText("25"), closeSoftKeyboard());
        onView(withId(R.id.cardBtn)).perform(scrollTo(), click());
        onView(withId(R.id.errorMessage))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(withText("Values cannot be negative")));

    }

    @Test
    public void feetNegativeValue(){
        onView(withId(R.id.edKg)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.edFeet)).perform(typeText("-5"), closeSoftKeyboard());
        onView(withId(R.id.edIns)).perform(typeText("7"), closeSoftKeyboard());
        onView(withId(R.id.edAge)).perform(typeText("25"), closeSoftKeyboard());
        onView(withId(R.id.cardBtn)).perform(scrollTo(), click());
        onView(withId(R.id.errorMessage))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(withText("Values cannot be negative")));

    }

    @Test
    public void inchNegativeValue(){
        onView(withId(R.id.edKg)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.edFeet)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.edIns)).perform(typeText("-7"), closeSoftKeyboard());
        onView(withId(R.id.edAge)).perform(typeText("25"), closeSoftKeyboard());
        onView(withId(R.id.cardBtn)).perform(scrollTo(), click());
        onView(withId(R.id.errorMessage))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(withText("Values cannot be negative")));

    }


    @Test
    public void ageNegativeValue(){
        onView(withId(R.id.edKg)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.edFeet)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.edIns)).perform(typeText("7"), closeSoftKeyboard());
        onView(withId(R.id.edAge)).perform(typeText("-25"), closeSoftKeyboard());
        onView(withId(R.id.cardBtn)).perform(scrollTo(), click());
        onView(withId(R.id.errorMessage))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(withText("Values cannot be negative")));

    }

    @Test
    public void inchValueMoreThan12(){
        onView(withId(R.id.edKg)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.edFeet)).perform(typeText("5"), closeSoftKeyboard());
        onView(withId(R.id.edIns)).perform(typeText("13"), closeSoftKeyboard());
        onView(withId(R.id.edAge)).perform(typeText("25"), closeSoftKeyboard());
        onView(withId(R.id.cardBtn)).perform(scrollTo(), click());
        onView(withId(R.id.errorMessage))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(withText("Inches must be less than 12")));

    }






}

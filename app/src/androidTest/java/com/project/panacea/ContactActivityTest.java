package com.project.panacea;

import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;

@RunWith(AndroidJUnit4.class)
public class ContactActivityTest {

    @Before
    public void setUp() {
        Intents.init();
        ActivityScenario.launch(ContactActivity.class);
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testPhoneNumberClick() {
        onView(withId(R.id.phoneNumber)).perform(click());
        intended(IntentMatchers.hasAction(Intent.ACTION_DIAL));
        intended(IntentMatchers.hasData(Uri.parse("tel:01575633177")));
    }

    @Test
    public void testEmailClick() {
        onView(withId(R.id.email)).perform(click());
        intended(IntentMatchers.hasAction(Intent.ACTION_SENDTO));
        intended(IntentMatchers.hasData(Uri.parse("mailto:sanzana526@gmail.com")));
    }

    @Test
    public void testAddressClick() {
        onView(withId(R.id.address)).perform(click());
        intended(IntentMatchers.hasAction(Intent.ACTION_VIEW));
        intended(IntentMatchers.hasData(Uri.parse("https://www.google.com/maps/search/KUET+map/@22.8992533,89.5018612,17z/data=!3m1!4b1?entry=ttu")));
    }
}

package com.project.panacea;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testLoginButtonNavigatesToLoginActivity() {
        try (ActivityScenario<WelcomeActivity> scenario = ActivityScenario.launch(WelcomeActivity.class)) {
            onView(withId(R.id.button2)).perform(click());
            intended(hasComponent(LoginActivity.class.getName()));
        }
    }

    @Test
    public void testRegisterButtonNavigatesToRegisterActivity() {
        try (ActivityScenario<WelcomeActivity> scenario = ActivityScenario.launch(WelcomeActivity.class)) {
            onView(withId(R.id.button)).perform(click());
            intended(hasComponent(RegisterActivity.class.getName()));
        }
    }
}

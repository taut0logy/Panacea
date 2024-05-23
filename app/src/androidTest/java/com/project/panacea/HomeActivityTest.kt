package com.project.panacea

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.remote.EspressoRemoteMessage
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
public class HomeActivityTest {

    @get: Rule
    var activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun testLaunchRecords() {
            Espresso.onView(ViewMatchers.withId(R.id.records)).perform(ViewActions.click())
            assertNotNull(ActivityScenario.launch(RecordActivity::class.java))
    }


    @Test
    fun testLaunchHealthTips() {
        Espresso.onView(ViewMatchers.withId(R.id.healthtip)).perform(ViewActions.click())
        assertNotNull(ActivityScenario.launch(HealthTipsActivity::class.java))
    }

    @Test
    fun testLaunchBMI() {
        Espresso.onView(ViewMatchers.withId(R.id.bmi)).perform(ViewActions.click())
        assertNotNull(ActivityScenario.launch(BMIActivity::class.java))
    }





}
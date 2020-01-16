package com.example.rrifafauzikomara.qrcodescanner;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class glavnayaTest {
    @Rule
    public ActivityTestRule<glavnaya> mActivityRule = new   ActivityTestRule<>( glavnaya.class);
    @Test
    public void monitorOpenClick() {
        Espresso.onView(ViewMatchers.withId(R.id.BTN_monitor)).perform(ViewActions.click());
    }

    @Test
    public void ozyOpenClick() {
        Espresso.onView(ViewMatchers.withId(R.id.BTNmon)).perform(ViewActions.click());
    }
}
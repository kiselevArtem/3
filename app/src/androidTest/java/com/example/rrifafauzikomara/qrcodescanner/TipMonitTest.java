package com.example.rrifafauzikomara.qrcodescanner;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class TipMonitTest {
    @Rule
    public ActivityTestRule<TipMonit> mActivityRule = new   ActivityTestRule<>( TipMonit.class);
    @Test
    public void BTN_add_tipMon() {
        Espresso.onView(ViewMatchers.withId(R.id.ET_Name)).perform(ViewActions.typeText("Qwere123"));
        Espresso.onView(ViewMatchers.withId(R.id.btnTip)).perform(ViewActions.click());
    }

}
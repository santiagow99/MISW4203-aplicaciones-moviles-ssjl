package com.example.vinyls_jetpack_application;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.vinyls_jetpack_application.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ArtistTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void navigateToArtistFragment() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.artistFragment)).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void testNavArtist() {
        onView(withId(R.id.artistFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testViewListArtist(){
        onView(withId(R.id.artistRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testScrollArtistList() {

        onView(withId(R.id.artistRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.artistRecyclerView)).perform(swipeUp());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.artistRecyclerView)).perform(swipeUp());
    }

}

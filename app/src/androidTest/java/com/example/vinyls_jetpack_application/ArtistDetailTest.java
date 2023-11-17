package com.example.vinyls_jetpack_application;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertNotEquals;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.vinyls_jetpack_application.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ArtistDetailTest {

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
    public void testFirstArtistAllComponents() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.artistRecyclerView)).perform(click());
        onView(withId(R.id.artistDetailImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.artistDetailNameTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.artistDetailDescriptionTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.artistDetailBirthdateTextView)).check(matches(isDisplayed()));

    }


    @Test
    public void testDifferentArtistsContent() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.artistRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        String firstArtistName = onView(withId(R.id.artistDetailNameTextView)).toString();
        String firstArtistDescription = onView(withId(R.id.artistDetailDescriptionTextView)).toString();


        Espresso.pressBack();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.artistRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        String secondArtistName = onView(withId(R.id.artistDetailNameTextView)).toString();
        String secondArtistDescription = onView(withId(R.id.artistDetailDescriptionTextView)).toString();


        assertNotEquals(firstArtistName, secondArtistName);
        assertNotEquals(firstArtistDescription, secondArtistDescription);
    }

}

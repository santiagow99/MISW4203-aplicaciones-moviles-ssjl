package com.example.vinyls_jetpack_application;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.junit.Assert.assertNotEquals;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.vinyls_jetpack_application.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
public class AlbumTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAllElements() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()));
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.albumCoverImageView)).check(matches(isDisplayed()));
        //onView(withId(R.id.textView6)).check(matches(isDisplayed()));
    }

    @Test
    public void testCompareAlbums() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.albumCarouselRv)).check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String album1Title = onView(withId(R.id.textView6)).toString();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.albumCarouselRv)).perform(swipeLeft());
        String album2Title = onView(withId(R.id.textView6)).toString();
        assertNotEquals(album1Title, album2Title);

    }

    @Test
    public void testSearch(){
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.searchEditText)).perform(typeText("test"));

        Espresso.closeSoftKeyboard();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.albumCarouselRv)).check(matches(isDisplayed()));
        //onView(withId(R.id.textView6)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptySearchResult() {
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.searchEditText)).perform(typeText("312312312312"));
        Espresso.closeSoftKeyboard();
    }

}

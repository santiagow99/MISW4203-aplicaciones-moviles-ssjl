package com.example.vinyls_jetpack_application;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.vinyls_jetpack_application.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CommentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void navigateToAlbumDetails() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.albumCarouselRv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.commentIcon)).perform(click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testContentPresence() {
        onView(withId(R.id.commentsRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.commentDescriptionEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.commentRatingEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.commentCollectorIdEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.submitButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddCommentAndVerify() {
        String commentText = "Nuevo comentario";
        String rating = "5";
        String collectorId = "1";

        onView(withId(R.id.commentDescriptionEditText)).perform(replaceText(commentText));
        onView(withId(R.id.commentRatingEditText)).perform(replaceText(rating));
        onView(withId(R.id.commentCollectorIdEditText)).perform(replaceText(collectorId));
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.commentsRecyclerView))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText(commentText))))
                .check(matches(hasDescendant(withText(collectorId))));
    }
}

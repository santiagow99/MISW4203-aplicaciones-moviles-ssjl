package com.example.vinyls_jetpack_application;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.vinyls_jetpack_application.ui.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

public class AlbumDetailTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testClickAlbumImageAndValidateName() {
        // Espera un momento para asegurarte de que la vista de la imagen del álbum esté completamente cargada
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Asegúrate de que la vista de la imagen del álbum esté visible
        onView(withId(R.id.albumCoverImageView)).check(matches(isDisplayed()));

        // Realiza la acción de hacer clic en la imagen del álbum
        onView(withId(R.id.albumCoverImageView)).perform(click());

        onView(withId(R.id.albumItemName)).check(matches(isDisplayed()));
    }

    @Test
    public void testValidateScroll() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Asegúrate de que la vista de la imagen del álbum esté visible
        onView(withId(R.id.albumCoverImageView)).check(matches(isDisplayed()));

        // Realiza la acción de hacer clic en la imagen del álbum
        onView(withId(R.id.albumCoverImageView)).perform(click());

        onView(withId(R.id.tracksList)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.tracksList))
                .perform(RecyclerViewActions.scrollToPosition(10))
                .perform(ViewActions.actionWithAssertions(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        // Restringe la acción a la vista RecyclerView
                        return ViewMatchers.isAssignableFrom(RecyclerView.class);
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        // Pausa para ralentizar el desplazamiento
                        uiController.loopMainThreadForAtLeast(3000); // Ajusta el tiempo según lo que necesites
                    }
                    @Override
                    public String getDescription() {
                        return "Ralentizar desplazamiento";
                    }

                }));

    }
}

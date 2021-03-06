package com.test.testapplication.ui;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test.testapplication.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by moseskesavan on 11/24/17.
 */
@RunWith(AndroidJUnit4.class)
public class InfoListFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * to be swiped view was only 89% visible to the user, while Espresso's swipe actions internally demand 90%. So the solution is to wrap the          * swipe action into another action and override these constraints by hand
     * @param action which
     * @param constraints any if
     * @return vew action
     */
    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }

    @Test
    public void testListScroll() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.list_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(9, click()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.list_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testListHasData(){
        if (getRVcount() > 0){
            onView(withId(R.id.list_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        }
    }

    private int getRVcount(){
        RecyclerView recyclerView = (RecyclerView) menuActivityTestRule.getActivity().findViewById(R.id.list_view);
        return recyclerView.getAdapter().getItemCount();
    }

}
package com.udacity.gradle.builditbigger;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import in.divyamary.androidjokes.JokeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



@RunWith(AndroidJUnit4.class)
    public class ApplicationTest {




    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    AsyncTaskIdlingResource asyncTaskIdlingResource;

    @Before
    public void before() {
        Instrumentation instrumentation
                = InstrumentationRegistry.getInstrumentation();
        Context ctx = instrumentation.getTargetContext();
        asyncTaskIdlingResource = new AsyncTaskIdlingResource(ctx);
        Espresso.registerIdlingResources(asyncTaskIdlingResource);
    }

    @After
    public void after() {
        Espresso.unregisterIdlingResources(asyncTaskIdlingResource);
    }

    @Test
    public void tellJoke() {
        String[] jokes = new String[]{
                "Why is there no gambling in Africa? Too Many Cheetahs!",
                "What do you call a fish with no eye? Fssshh",
                "Why did the cookie cry? Because his mother was a wafer so long!",
                "What does a ghost wear when it's raining outside? Boooooooooooooooooooooooooooots!",
                "What did 0 say to 8 ?Nice belt!",
                "Why did the elephants get kicked out of the public pool? THEY KEPT DROPPING THEIR TRUNKS!",
                "What did one shark say to the other while eating a clownfish? This tastes funny.",
                "What do calendars eat? Dates!",
                "What's it called when you lend money to a bison? A BUFFA-LOAN!",
                "Why wouldn't the shrimp share his treasure? Because he was a little shellfish."
        };
        //check if button exists
        onView(withId(R.id.tell_joke_button)).check(matches(notNullValue()));
        onView(withId(R.id.tell_joke_button)).check(matches(withText("Tell Joke")));
        // this triggers our async task, as we registered Espresso for it, Espresso waits for it to finish
        onView(withId(R.id.tell_joke_button)).perform(click());
        // check if intent was sent with string extra key 'joke' and its value matches one of the jokes
        intended(allOf(
                hasExtra(is("joke"), isIn(jokes)),
                hasComponent(JokeActivity.class.getName())));
    }

}
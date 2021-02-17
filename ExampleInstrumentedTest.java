package com.example.myfirstwebbrowser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;

import androidx.annotation.ContentView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

// Verification of the build is working
   @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myfirstwebbrowser", appContext.getPackageName());
    }

//Verification of the Enter URL text box and Go button to make sure web page is navigating
    @Test
    public void enterURL(){
        onView(withId(R.id.editText)).perform(typeText("amazon.com"));
        onView(withId(R.id.button)).perform(click());
        ViewInteraction webView = onView(
                allOf(withId(R.id.webview),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        webView.check(matches(isDisplayed()));
    }

//Verification of the Back button to make sure user can navigate back to the previous web page.
    @Test
    public void backButton(){
        onView(withId(R.id.editText)).perform(typeText("twitter.com"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        ViewInteraction webView = onView(
                allOf(withId(R.id.webview),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        webView.check(matches(isDisplayed()));
    }

    //Verification of the forward button to make sure user can navigate forward again to the previous web page.
    @Test
    public void forwardButton() {
        onView(withId(R.id.editText)).perform(typeText("w3schools.com"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.forwardButton)).perform(click());
        ViewInteraction webView = onView(
                allOf(withId(R.id.webview),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        webView.check(matches(isDisplayed()));
    }

    //Verification of the Home button to make sure user navigates to the home web page (google.com).
    @Test
    public void homeButton() {
        onView(withId(R.id.editText)).perform(typeText("w3schools.com"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.homedButton)).perform(click());
        ViewInteraction webView = onView(
                allOf(withId(R.id.webview),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        webView.check(matches(isDisplayed()));
    }

    //Verification of the Reload button to make sure user can reload the same web page.
    @Test
    public void reloadButton() {
        onView(withId(R.id.editText)).perform(typeText("w3schools.com"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.replayButton)).perform(click());

        ViewInteraction webView = onView(
                allOf(withId(R.id.webview),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        webView.check(matches(isDisplayed()));
    }

    //Verification of the History button to make sure user can navigate to the History page and can see the History.
    @Test
    public void historyButton() {
        onView(withId(R.id.editText)).perform(typeText("w3schools.com"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.historyButton)).perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.txtViewTitle), withText("W3Schools Online Web Tutorials"),
                        withParent(allOf(withId(R.id.relativeLayout1),
                                withParent(withId(R.id.listView)))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    //Verification of the Bookmark button to make sure user can navigate to the Bookmark page and see the bookmarked web pages.
   @Test
        public void BookmarkButton(){
            onView(withId(R.id.editText)).perform(typeText("amazon.com"));
            onView(withId(R.id.button)).perform(click());
            onView(withId(R.id.bookButton)).perform(click());
            onView(withId(R.id.bookmarkButton)).perform(click());
       ViewInteraction linearLayout = onView(
               allOf(withParent(allOf(withId(android.R.id.content),
                       withParent(withId(R.id.decor_content_parent)))),
                       isDisplayed()));
       linearLayout.check(matches(isDisplayed()));
    }

}
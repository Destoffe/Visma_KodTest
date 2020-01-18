package com.stoffe.visma;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static junit.framework.TestCase.assertNotNull;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp() {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        View view =  mainActivity.findViewById(R.id.container);
        assertNotNull(view);
    }

    @After
    public void tearDown() {

        mainActivity = null;
    }
}
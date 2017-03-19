package com.sromku.sample.runtests.basic;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sromku.sample.runtests.MainActivity;
import com.sromku.sample.runtests.Utils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    public void useAppContextSoundsWrong() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(2000);
        assertEquals("com.sromku.sample.runtests.wrong", appContext.getPackageName());
    }

}

package com.sromku.sample.runtests.more;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sromku.sample.runtests.ClearData;
import com.sromku.sample.runtests.MainActivity;

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
        sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void useAppContextAnother() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    private static void sleep(long mls) {
        try {
            Thread.sleep(mls);
        } catch (InterruptedException e) {
            // do nothing
        }
    }


}

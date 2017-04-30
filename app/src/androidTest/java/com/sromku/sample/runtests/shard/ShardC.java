package com.sromku.sample.runtests.shard;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sromku.sample.runtests.ClearData;
import com.sromku.sample.runtests.MainActivity;
import com.sromku.sample.runtests.Utils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

/**
 * Created by sromku on April, 2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShardC {

    private final static int SMALL_TEST = 1000;
    private final static int LARGE_TEST = 10000;

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    @Test
    @ClearData
    public void testA() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testB() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testC() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testD() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(LARGE_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testE() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testF() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testG() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testH() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(LARGE_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testI() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testJ() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }
}

package com.sromku.sample.runtests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    public void useAppContextSoundsWrong() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.sromku.sample.runtests.wrong", appContext.getPackageName());
    }

}

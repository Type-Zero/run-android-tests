package com.sromku.sample.runtests.all;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sromku.sample.runtests.ClearData;
import com.sromku.sample.runtests.MainActivity;
import com.sromku.sample.runtests.Parameterized;
import com.sromku.sample.runtests.Tags;
import com.sromku.sample.runtests.Utils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AllOne {

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    private final String[] params = new String[]{
            "a", "A", "Aa"
    };

    @Test
    public void testA() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    @Tags(tags = {"extreme"})
    public void testB() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Utils.sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    @Tags(tags = {"sanity", "medium"})
    @Parameterized.Repeat(count = 3)
    public void testC() throws Exception {
        int index = Parameterized.getIndex();
        if (index < 0) {
            return;
        }
        String param = params[index];
        Utils.sleep(2000);
        assertEquals("a", param.toLowerCase());
    }

}

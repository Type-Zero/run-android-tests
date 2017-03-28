package com.sromku.sample.runtests.param;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sromku.sample.runtests.ClearData;
import com.sromku.sample.runtests.MainActivity;
import com.sromku.sample.runtests.Parameterized;
import com.sromku.sample.runtests.Utils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleOne {

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    private final String[] params = new String[]{
            "a", "b", "c"
    };

    @Test
    @Parameterized.Repeat(count = 2)
    @ClearData
    public void testA() throws Exception {
        int index = Parameterized.getIndex();
        if (index < 0) {
            return;
        }
        String param = params[index];
        Utils.sleep(2000);
        assertEquals("a", param);
    }

    @Test
    @Parameterized.Repeat(count = 3)
    public void testB() throws Exception {
        int index = Parameterized.getIndex();
        if (index < 0) {
            return;
        }
        String param = params[index];
        Utils.sleep(2000);
        assertEquals("a", param);
    }

    @Test
    public void testC() throws Exception {
        Utils.sleep(2000);
        assertEquals("a", "a");
    }

}

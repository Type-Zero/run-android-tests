package com.sromku.sample.runtests;

/**
 * Created by sromku on March, 2017.
 */
public class Utils {

    public static void sleep(long mls) {
        try {
            Thread.sleep(mls);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

}

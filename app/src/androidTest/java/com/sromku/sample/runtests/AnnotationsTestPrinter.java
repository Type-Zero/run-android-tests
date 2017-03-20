package com.sromku.sample.runtests;

import android.os.Bundle;
import android.support.test.internal.runner.listener.InstrumentationResultPrinter;
import android.support.test.internal.runner.listener.InstrumentationRunListener;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Created by sromku.
 */
public class AnnotationsTestPrinter extends InstrumentationRunListener {

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);

        Collection<Annotation> annotations = description.getAnnotations();
        if (annotations == null) {
            return;
        }

        Bundle bundle = new Bundle();
        StringBuilder stringBuilder = new StringBuilder();
        boolean comm = false;
        for (Annotation annotation : annotations) {
            if (comm) stringBuilder.append(",");
            stringBuilder.append(annotation.annotationType().getSimpleName());

            // special case of Repeat
            if (annotation instanceof Parameterized.Repeat) {
                Parameterized.Repeat repeat = (Parameterized.Repeat) annotation;
                stringBuilder.append(":" + repeat.count());
            }

            // special case of Tags
            if (annotation instanceof Tags) {
                Tags tags = (Tags) annotation;
                printTags(tags.tags());
            }
            comm = true;
        }

        bundle.putString("annotations", stringBuilder.toString());
        getInstrumentation().sendStatus(InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);

    }

    private void printTags(String[] tags) {
        Bundle bundle = new Bundle();
        StringBuilder stringBuilder = new StringBuilder();
        boolean comm = false;
        for (String tag : tags) {
            if (comm) stringBuilder.append(",");
            stringBuilder.append(tag);
            comm = true;
        }
        bundle.putString("tags", stringBuilder.toString());
        getInstrumentation().sendStatus(InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);
    }
}
